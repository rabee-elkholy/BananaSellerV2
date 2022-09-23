package com.androdu.bananaSeller.view.fragment.homeCycle.home;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_LOGGED;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataBoolean;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.ApiErrorHandler.showErrorMessage;
import static com.androdu.bananaSeller.helper.HelperMethod.hideView;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showView;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.OrdersAdapter;
import com.androdu.bananaSeller.data.model.Filter;
import com.androdu.bananaSeller.data.model.response.orders.Order;
import com.androdu.bananaSeller.data.model.response.orders.OrdersResponse;
import com.androdu.bananaSeller.helper.OnEndLess;
import com.androdu.bananaSeller.view.activity.SecondHomeActivity;
import com.androdu.bananaSeller.view.fragment.BottomSheetFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("NonConstantResourceId")
public class OrdersFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.app_bar_filter)
    ImageButton appBarFilter;
    @BindView(R.id.fragment_orders_rv_recycler_view)
    RecyclerView fragmentOrdersRvRecyclerView;
    @BindView(R.id.fragment_orders_srl_swipe)
    SwipeRefreshLayout fragmentOrdersSrlSwipe;
    @BindView(R.id.fragment_orders_load_more)
    RelativeLayout loadMore;
    @BindView(R.id.fragment_orders_pb_progress_bar)
    ProgressBar fragmentOrdersPbProgressBar;
    @BindView(R.id.fragment_orders_tv_msg)
    TextView fragmentOrdersTvMsg;
    @BindView(R.id.app_bar_sorting)
    ImageButton appBarSorting;
    private ArrayList<Order> offersList;
    private OnEndLess onEndLess;
    private Integer maxPage = 0;
    private OrdersAdapter ordersAdapter;
    private int sort = 2;
    private double deliveryPrice;
    private List<Filter> filters = new ArrayList<>();


    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, view);
        init();
        if (loadDataBoolean(getActivity(), USER_LOGGED))
            getOrders(1);
        else
            getOrdersGuest(1);

        return view;
    }

    private void init() {
        appBarTitle.setText(R.string.orders);
        appBarBack.setVisibility(View.GONE);
        appBarFilter.setVisibility(View.VISIBLE);
        appBarSorting.setVisibility(View.VISIBLE);

        String[] filtersArr = getResources().getStringArray(R.array.order_filter);
        filters.add(new Filter(filtersArr[1], 1));
        filters.add(new Filter(filtersArr[2], 2));
        filters.get(1).setChecked(true);
        filters.add(new Filter(filtersArr[3], 3));


        offersList = new ArrayList<>();

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        fragmentOrdersRvRecyclerView.setLayoutManager(linearLayout);


        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        if (loadDataBoolean(getActivity(), USER_LOGGED))
                            getOrders(current_page);
                        else
                            getOrdersGuest(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        fragmentOrdersRvRecyclerView.addOnScrollListener(onEndLess);

        ordersAdapter = new OrdersAdapter(getActivity(), offersList);
        fragmentOrdersRvRecyclerView.setAdapter(ordersAdapter);
        ordersAdapter.SetOnItemClickListener((position, model) -> {
            Intent intent = new Intent(getContext(), SecondHomeActivity.class);
            intent.putExtra("id", 4);
            intent.putExtra("orderId", model.getOrderDetails().getId());
            intent.putExtra("delivery_price", deliveryPrice);
            startActivity(intent);
        });
        fragmentOrdersSrlSwipe.setOnRefreshListener(() -> {
            if (loadDataBoolean(getActivity(), USER_LOGGED))
                getOrders(1);
            else
                getOrdersGuest(1);
            fragmentOrdersSrlSwipe.setRefreshing(false);
        });
    }


    private void getOrders(int page) {
        if (isConnected(getContext())) {
            if (page == 1)
                showView(fragmentOrdersPbProgressBar);
            else
                showView(loadMore);
            getClient().getOrders(loadDataString(getActivity(), TOKEN),
                            page,
                            sort,
                            getFilter())
                    .enqueue(new Callback<OrdersResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<OrdersResponse> call, @NonNull Response<OrdersResponse> response) {
                            hideView(fragmentOrdersPbProgressBar, loadMore);

                            if (response.isSuccessful()) {
                                onGetOrdersSuccess(response.body(), page);

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                showErrorMessage(requireActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<OrdersResponse> call, @NonNull Throwable t) {
                            hideView(fragmentOrdersPbProgressBar, loadMore);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }

    }

    private void getOrdersGuest(int page) {
        if (isConnected(getContext())) {
            if (page == 1)
                showView(fragmentOrdersPbProgressBar);
            else
                showView(loadMore);
            getClient().getOrdersGuest(page, sort, getFilter())
                    .enqueue(new Callback<OrdersResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<OrdersResponse> call, @NonNull Response<OrdersResponse> response) {
                            hideView(fragmentOrdersPbProgressBar, loadMore);

                            if (response.isSuccessful()) {
                                onGetOrdersSuccess(response.body(), page);

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                showErrorMessage(requireActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<OrdersResponse> call, @NonNull Throwable t) {
                            hideView(fragmentOrdersPbProgressBar, loadMore);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }

    }

    private List<String> getFilter() {
        List<String> requestFilter = new ArrayList<>();
        for (int i = 0; i < filters.size(); i++) {
            if (filters.get(i).isChecked()) {
                requestFilter.add(String.valueOf(filters.get(i).getKey()));
                Log.d("debugging", "getFilter: " + filters.get(i).getKey());
            }
        }

        return requestFilter;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onGetOrdersSuccess(OrdersResponse body, int page) {
        if (page == 1) {
            initOnEndLess();
            offersList.clear();
        }

        if (body.getTotal() != 0) {
            fragmentOrdersTvMsg.setText("");
            offersList.addAll(body.getOrders());
            maxPage = (body.getTotal() + 10 - 1) / 10;
        } else {
            offersList.addAll(new ArrayList<>());
            fragmentOrdersTvMsg.setText(R.string.no_orders_found);
        }
        ordersAdapter.notifyDataSetChanged();
        deliveryPrice = body.getBananaDeliveryPrice();
    }

    private void initOnEndLess() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (offersList != null) {
            if (loadDataBoolean(getActivity(), USER_LOGGED))
                getOrders(1);
            else {
                getOrdersGuest(1);
                appBarFilter.setVisibility(View.GONE);
                appBarSorting.setVisibility(View.GONE);
            }
        }
    }


    @OnClick({R.id.app_bar_filter, R.id.app_bar_sorting})
    public void onViewClicked(View view) {
        int sortTemp;
        switch (view.getId()) {
            case R.id.app_bar_filter:
                List<Filter> filtersTemp = new ArrayList<>();
                for (Filter filter : filters) {
                    filtersTemp.add(filter.clone());
                }
                Log.d("filters", "onViewClicked: " + filtersTemp.size());
                BottomSheetFragment bottomSheetDialog1 = new BottomSheetFragment(8, filtersTemp);
                bottomSheetDialog1.show(getParentFragmentManager(), "Custom Bottom Sheet");
                bottomSheetDialog1.SetOnConfirmClickListener(sheetFilters -> {

                    filters = sheetFilters;

                    getOrders(1);
                });
                break;
            case R.id.app_bar_sorting:
                Log.d("debugging", "init: ");
                sortTemp = sort;
                BottomSheetFragment bottomSheetDialog2 = new BottomSheetFragment(6, sortTemp);
                bottomSheetDialog2.show(getParentFragmentManager(), "Custom Bottom Sheet");
                bottomSheetDialog2.SetOnConfirmClickListener(sheetFilters -> {

                    sort = sheetFilters.get(0).getKey();

                    getOrders(1);
                });
                break;

        }
    }
}