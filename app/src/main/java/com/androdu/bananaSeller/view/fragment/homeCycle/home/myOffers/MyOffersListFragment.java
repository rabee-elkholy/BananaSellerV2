package com.androdu.bananaSeller.view.fragment.homeCycle.home.myOffers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.MyOffersAdapter;
import com.androdu.bananaSeller.data.model.requestBody.CancelOrderRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.data.model.response.offers.MyOffersResponse;
import com.androdu.bananaSeller.data.model.response.offers.Offer;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.HelperMethod;
import com.androdu.bananaSeller.helper.OnEndLess;
import com.androdu.bananaSeller.view.fragment.BottomSheetFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.ClientInfoFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.WalletFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.disappearKeypad;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.hideView;
import static com.androdu.bananaSeller.helper.HelperMethod.replaceFragment;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showView;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class MyOffersListFragment extends Fragment {

    @BindView(R.id.fragment_orders_list_rv_recycler_view)
    RecyclerView fragmentOrdersListRvRecyclerView;
    @BindView(R.id.fragment_orders_list_srl_swipe)
    SwipeRefreshLayout fragmentOrdersListSrlSwipe;
    @BindView(R.id.fragment_orders_list_pb_progress)
    ProgressBar fragmentOrdersListPbProgress;
    @BindView(R.id.fragment_orders_list_tv_msg)
    TextView fragmentOrdersListTvMsg;
    @BindView(R.id.fragment_orders_load_more)
    RelativeLayout loadMore;

    private View view;
    private String type;
    private ArrayList<Offer> offers;
    private MyOffersAdapter myOffersAdapter;

    private OnEndLess onEndLess;
    private Integer maxPage = 0;


    public MyOffersListFragment() {
        // Required empty public constructor
    }

    public MyOffersListFragment(String type) {
        // Required empty public constructor
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_offers_list, container, false);
        ButterKnife.bind(this, view);

        init();
        getMyOffers(1);
        return view;
    }

    private void init() {
        offers = new ArrayList<>();

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        fragmentOrdersListRvRecyclerView.setLayoutManager(linearLayout);


        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        getMyOffers(current_page);
                        disappearKeypad(getActivity());
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        fragmentOrdersListRvRecyclerView.addOnScrollListener(onEndLess);

        myOffersAdapter = new MyOffersAdapter(getActivity(), offers, type);
        fragmentOrdersListRvRecyclerView.setAdapter(myOffersAdapter);
        myOffersAdapter.SetOnItemClickListener(new MyOffersAdapter.OnItemClickListener() {
            @Override
            public void onClickDelivered(int position, Offer model) {
                orderDelivered(model.getOrder().getId());
            }

            @Override
            public void onClickClientInfo(int position, Offer model) {
//                replaceFragment(getParentFragmentManager(),
//                        R.id.activity_second_home_container,
//                        new ClientInfoFragment(model.getId()),
//                        true);
                ClientInfoFragment bottomSheetDialog = new ClientInfoFragment(model.getId(),1);
                bottomSheetDialog.show(getParentFragmentManager(), "Custom Bottom Sheet");

            }
        });

        fragmentOrdersListSrlSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyOffers(1);
                fragmentOrdersListSrlSwipe.setRefreshing(false);
            }
        });

    }


    private void getMyOffers(int page) {
        if (isConnected(getContext())) {
            if (page == 1)
                showView(fragmentOrdersListPbProgress);
            else
                showView(loadMore);
            getClient().getMyOffers(loadDataString(getActivity(), TOKEN), page, type)
                    .enqueue(new Callback<MyOffersResponse>() {
                        @Override
                        public void onResponse(Call<MyOffersResponse> call, Response<MyOffersResponse> response) {
                            hideView(fragmentOrdersListPbProgress, loadMore);

                            if (response.isSuccessful()) {
                                onGetOrdersSuccess(response.body(), page);

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<MyOffersResponse> call, Throwable t) {
                            hideView(fragmentOrdersListPbProgress, loadMore);
                            Log.d("error_handler", "onFailure: " + t.getMessage());
                            t.printStackTrace();

                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    private void orderDelivered(String offerId) {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().offerDelivered(loadDataString(getActivity(), TOKEN), new CancelOrderRequestBody(offerId))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();
                            if (response.isSuccessful()) {
                                showSuccessDialog(getActivity(), getString(R.string.done));
                                getMyOffers(1);
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<GeneralResponse> call, Throwable t) {
                            dismissProgressDialog();
                            Log.d("error_handler", "onFailure: " + t.getMessage());
                            t.printStackTrace();

                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    private void onGetOrdersSuccess(MyOffersResponse myOffersResponse, int page) {

        if (page == 1) {
            initOnEndLess();
            offers.clear();
        }

        if (myOffersResponse.getTotal() != 0) {
            fragmentOrdersListTvMsg.setText("");
            offers.addAll(myOffersResponse.getData());
            maxPage = (myOffersResponse.getTotal() + 10 - 1) / 10;
        } else {
            offers.addAll(new ArrayList<>());
            fragmentOrdersListTvMsg.setText(R.string.no_orders_found);
        }
        myOffersAdapter.notifyDataSetChanged();
    }

    private void initOnEndLess() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
    }

}