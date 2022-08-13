package com.androdu.bananaSeller.view.fragment.homeCycle.delivery;

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
import com.androdu.bananaSeller.adapter.DeliveryOffersAdapter;
import com.androdu.bananaSeller.adapter.MyOffersAdapter;
import com.androdu.bananaSeller.data.model.requestBody.CancelOrderRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.data.model.response.offers.MyOffersResponse;
import com.androdu.bananaSeller.data.model.response.offers.Offer;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.OnEndLess;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.ClientInfoFragment;

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
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showView;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class DeliveryListFragment extends Fragment {

    @BindView(R.id.fragment_delivery_list_rv_recycler_view)
    RecyclerView fragmentDeliveryListRvRecyclerView;
    @BindView(R.id.fragment_delivery_list_srl_swipe)
    SwipeRefreshLayout fragmentDeliveryListSrlSwipe;
    @BindView(R.id.fragment_delivery_load_more)
    RelativeLayout loadMore;
    @BindView(R.id.fragment_delivery_list_pb_progress)
    ProgressBar fragmentDeliveryListPbProgress;
    @BindView(R.id.fragment_delivery_list_tv_msg)
    TextView fragmentDeliveryListTvMsg;
    private View view;

    private int type;
    private ArrayList<Offer> offers;
    private DeliveryOffersAdapter myOffersAdapter;

    private OnEndLess onEndLess;
    private Integer maxPage = 0;


    public DeliveryListFragment() {
        // Required empty public constructor
    }

    public DeliveryListFragment(int type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delivery_list, container, false);
        ButterKnife.bind(this, view);
        init();
        getMyOffers(1);
        return view;
    }

    private void init() {
        offers = new ArrayList<>();

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        fragmentDeliveryListRvRecyclerView.setLayoutManager(linearLayout);


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
        fragmentDeliveryListRvRecyclerView.addOnScrollListener(onEndLess);

        myOffersAdapter = new DeliveryOffersAdapter(getActivity(), offers, String.valueOf(type));
        fragmentDeliveryListRvRecyclerView.setAdapter(myOffersAdapter);
        myOffersAdapter.SetOnItemClickListener(new DeliveryOffersAdapter.OnItemClickListener() {
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
                ClientInfoFragment bottomSheetDialog = new ClientInfoFragment(model.getId(),2);
                bottomSheetDialog.show(getParentFragmentManager(), "Custom Bottom Sheet");

            }

            @Override
            public void onClickSellerInfo(int position, Offer model) {
                ClientInfoFragment bottomSheetDialog = new ClientInfoFragment(model.getId(),3);
                bottomSheetDialog.show(getParentFragmentManager(), "Custom Bottom Sheet");

            }
        });

        fragmentDeliveryListSrlSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyOffers(1);
                fragmentDeliveryListSrlSwipe.setRefreshing(false);
            }
        });

    }


    private void getMyOffers(int page) {
        if (isConnected(getContext())) {
            if (page == 1)
                showView(fragmentDeliveryListPbProgress);
            else
                showView(loadMore);
            getClient().getDeliveryOffers(loadDataString(getActivity(), TOKEN), page, type)
                    .enqueue(new Callback<MyOffersResponse>() {
                        @Override
                        public void onResponse(Call<MyOffersResponse> call, Response<MyOffersResponse> response) {
                            hideView(fragmentDeliveryListPbProgress, loadMore);

                            if (response.isSuccessful()) {
                                onGetOrdersSuccess(response.body(), page);

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<MyOffersResponse> call, Throwable t) {
                            hideView(fragmentDeliveryListPbProgress, loadMore);
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
            getClient().offerDeliveredD(loadDataString(getActivity(), TOKEN), new CancelOrderRequestBody(offerId))
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
            fragmentDeliveryListTvMsg.setText("");
            offers.addAll(myOffersResponse.getData());
            maxPage = (myOffersResponse.getTotal() + 10 - 1) / 10;
        } else {
            offers.addAll(new ArrayList<>());
            fragmentDeliveryListTvMsg.setText(R.string.no_orders_found);
        }
        myOffersAdapter.notifyDataSetChanged();
    }

    private void initOnEndLess() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
    }

}