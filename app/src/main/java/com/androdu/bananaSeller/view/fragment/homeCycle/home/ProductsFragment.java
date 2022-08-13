package com.androdu.bananaSeller.view.fragment.homeCycle.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.GuestProductsAdapter;
import com.androdu.bananaSeller.data.model.Filter;
import com.androdu.bananaSeller.data.model.response.products.Product;
import com.androdu.bananaSeller.data.model.response.products.ProductsResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.OnEndLess;
import com.androdu.bananaSeller.view.fragment.BottomSheetFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.disappearKeypad;
import static com.androdu.bananaSeller.helper.HelperMethod.hideView;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showView;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class ProductsFragment extends Fragment {
    // F / F-V / F-M / B

    public final static String fruits_and_vegetables = "F-V";
    public final static String fish_and_meat = "F-M";
    public final static String baked_goods = "B";
    public final static String food = "F";

    private final static int TYPE_PRODUCTS = 1;
    private final static int TYPE_SEARCH = 2;

    private int currentProductsType = 1;


    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_products_rv_recycler_view)
    RecyclerView fragmentProductsRvRecyclerView;
    @BindView(R.id.fragment_products_srl_swipe)
    SwipeRefreshLayout fragmentProductsSrlSwipe;
    @BindView(R.id.filter_tit_search)
    TextInputEditText filterTitSearch;
    @BindView(R.id.filter_btn_filters)
    ImageButton filterBtnFilters;
    @BindView(R.id.fragment_products_load_more)
    RelativeLayout fragmentProductsLoadMore;
    @BindView(R.id.home_fragment_pb_progress_bar)
    ProgressBar homeFragmentPbProgressBar;
    @BindView(R.id.products_fragment_tv_msg)
    TextView productsFragmentTvMsg;
    @BindView(R.id.fragment_products_btn_add_product)
    Button fragmentProductsBtnAddProduct;

    private View view;
    private List<Product> products;
    private GuestProductsAdapter productsAdapter;

    private String category;
    private int type;

    private List<Filter> filters = new ArrayList<>();
    private OnEndLess onEndLess;
    private Integer maxPage = 0;

    private AlertDialog alertDialog;

    private int count = 1;
    private String unit;
    private int unitIndex;


    public ProductsFragment() {
        // Required empty public constructor
    }

    public ProductsFragment(String category, int type) {
        this.category = category;
        this.type = type;
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
        view = inflater.inflate(R.layout.fragment_guest_products, container, false);
        ButterKnife.bind(this, view);
        init();

        getProductsGuest(1);
        return view;
    }

    private void init() {
        setPageTitle();

        filters.add(new Filter(getString(R.string.most_wanted), 1));
        filters.add(new Filter(getString(R.string.recently_added), 1));
        // //
        filters.get(0).setChecked(true);
        filters.get(1).setChecked(true);
        // //
        products = new ArrayList<>();

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        fragmentProductsRvRecyclerView.setLayoutManager(linearLayout);


        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        if (currentProductsType == TYPE_PRODUCTS) {
                            getProductsGuest(current_page);
                        } else {

                            searchProductsGuest(current_page);
                        }

                        disappearKeypad(getActivity());
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        fragmentProductsRvRecyclerView.addOnScrollListener(onEndLess);

        productsAdapter = new GuestProductsAdapter(getActivity(), products);
        fragmentProductsRvRecyclerView.setAdapter(productsAdapter);


        fragmentProductsSrlSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getProductsGuest(1);
                disappearKeypad(getActivity());
                fragmentProductsSrlSwipe.setRefreshing(false);
            }
        });


    }


    private void setPageTitle() {
        switch (category) {
            case fruits_and_vegetables:
                appBarTitle.setText(getString(R.string.fruits_and_vegetables));
                break;
            case fish_and_meat:
                appBarTitle.setText(getString(R.string.meat_and_fish));
                break;
            case food:
                appBarTitle.setText(getString(R.string.food));
                break;
            case baked_goods:
                appBarTitle.setText(getString(R.string.baked_goods));
                break;
        }
    }


    @OnClick({R.id.app_bar_back, R.id.filter_btn_search, R.id.filter_btn_filters, R.id.fragment_products_btn_add_product})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                getActivity().onBackPressed();
                break;
            case R.id.filter_btn_search:
                searchProductsGuest(1);
                disappearKeypad(getActivity());
                break;
            case R.id.filter_btn_filters:
                List<Filter> filtersTemp = new ArrayList<>();
                for (Filter filter : filters) {
                    filtersTemp.add(filter.clone());
                }
                Log.d("debugging", "type: " + type);

                BottomSheetFragment bottomSheetDialog = new BottomSheetFragment(type, filtersTemp);
                bottomSheetDialog.show(getParentFragmentManager(), "Custom Bottom Sheet");
                bottomSheetDialog.SetOnConfirmClickListener(new BottomSheetFragment.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(List<Filter> sheetFilters) {

                        Log.d("debugging", "onConfirm: " + sheetFilters.size());
                        for (Filter filter : sheetFilters) {
                            Log.d("debugging", "onConfirm: " + filter.isChecked());
                        }

                        filters = sheetFilters;


                        getProductsGuest(1);
                    }
                });
                break;


        }
    }


    private void getProductsGuest(int page) {
        if (isConnected(getContext())) {
            resetFilters();
            filterTitSearch.setText("");
            if (page == 1)
                showView(homeFragmentPbProgressBar);
            else
                showView(fragmentProductsLoadMore);

            getClient().getProductsGuest(category,
                    loadDataString(getActivity(), TOKEN),
                    page,
                    filters.get(filters.size() - 2).getKey(),
                    filters.get(filters.size() - 1).getKey(),
                    getFilter())
                    .enqueue(new Callback<ProductsResponse>() {
                        @Override
                        public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                            hideView(homeFragmentPbProgressBar, fragmentProductsLoadMore);

                            if (response.isSuccessful()) {
                                onGetProductsSuccess(response.body(), page);
                                currentProductsType = TYPE_PRODUCTS;

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductsResponse> call, Throwable t) {
                            hideView(homeFragmentPbProgressBar, fragmentProductsLoadMore);

                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }


    private void searchProductsGuest(int page) {
        if (isConnected(getContext())) {
            showView(homeFragmentPbProgressBar);
            String searchQ = filterTitSearch.getText().toString().trim();
            getClient().searchProductsGuest(category, loadDataString(getActivity(), TOKEN), page, searchQ)
                    .enqueue(new Callback<ProductsResponse>() {
                        @Override
                        public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                            hideView(homeFragmentPbProgressBar);

                            if (response.isSuccessful()) {
                                onGetProductsSuccess(response.body(), page);
                                currentProductsType = TYPE_SEARCH;
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductsResponse> call, Throwable t) {
                            hideView(homeFragmentPbProgressBar);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }


    private void onGetProductsSuccess(ProductsResponse productsResponse, int page) {

        if (page == 1) {
            initOnEndLess();
            products.clear();
        }

        if (productsResponse.getTotal() != 0) {
            productsFragmentTvMsg.setText("");
            products.addAll(productsResponse.getProducts());
            maxPage = (productsResponse.getTotal() + 10 - 1) / 10;
        } else {
            products.addAll(new ArrayList<>());
            productsFragmentTvMsg.setText(R.string.no_products_found);
        }
        productsAdapter.notifyDataSetChanged();
    }


    private void resetFilters() {
        int size = filters.size();
        if (filters.get(size - 2).isChecked())
            filters.get(size - 2).setKey(1);
        else
            filters.get(size - 2).setKey(0);

        if (filters.get(size - 1).isChecked())
            filters.get(size - 1).setKey(1);
        else
            filters.get(size - 1).setKey(0);
    }

    private List<String> getFilter() {
        List<String> requestFilter = new ArrayList<>();

        for (int i = 0; i < filters.size() - 2; i++) {
            if (filters.get(i).isChecked()) {
                requestFilter.add(String.valueOf(filters.get(i).getKey()));
                Log.d("debugging", "getFilter: " + filters.get(i).getKey());
            }
        }

        return requestFilter;
    }

    private void initOnEndLess() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
    }


}