package com.androdu.bananaSeller.view.fragment.homeCycle.home;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.ProductsAdapter;
import com.androdu.bananaSeller.data.model.requestBody.addOffer.AddOfferRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.addOffer.OfferItem;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.data.model.response.order.OrderResponse;
import com.androdu.bananaSeller.data.model.response.orders.OrderProduct;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.Validation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialogCloseFragment;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class AddOfferFragment extends Fragment {

    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_add_offer_rc_items)
    RecyclerView fragmentAddOfferRcItems;
    @BindView(R.id.fragment_add_offer_til_total_price)
    TextInputLayout fragmentAddOfferTilTotalPrice;
    @BindView(R.id.fragment_add_offer_rg_delivery)
    RadioGroup fragmentAddOfferRgDelivery;
    private View view;
    private String orderId;

    private List<OrderProduct> orderProducts;
    private List<OfferItem> amountArr;
    private ProductsAdapter productsAdapter;
    private LatLng orderLocation;
    private boolean bananaDelivery = false;
    private boolean isEmptyOffer;
    private double deliveryPrice;
    private double price;

    public AddOfferFragment() {
        // Required empty public constructor
    }

    public AddOfferFragment(String orderId, double deliveryPrice) {
        this.orderId = orderId;
        this.deliveryPrice = deliveryPrice;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_offer, container, false);
        ButterKnife.bind(this, view);

        init();
        getOrder();

        return view;
    }

    private void init() {
        appBarTitle.setText(R.string.add_offer);

//        fragmentAddOfferRgDelivery.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == R.id.fragment_add_offer_rb_banana_delivery)
//                    bananaDelivery = true;
//                else
//                    bananaDelivery = false;
//            }
//        });

        orderProducts = new ArrayList<>();
        productsAdapter = new ProductsAdapter(getActivity(), orderProducts);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        fragmentAddOfferRcItems.setLayoutManager(linearLayout);
        fragmentAddOfferRcItems.setAdapter(productsAdapter);

    }

    private void getOrder() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().getOrder(loadDataString(getActivity(), TOKEN), orderId)
                    .enqueue(new Callback<OrderResponse>() {
                        @Override
                        public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                            dismissProgressDialog();
                            if (response.isSuccessful()) {
                                onGetOrderSuccess(response.body());

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderResponse> call, Throwable t) {
                            dismissProgressDialog();
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }

    }

    private void onGetOrderSuccess(OrderResponse body) {
        orderLocation = new LatLng(body.getOrder().getLocation().getCoordinates().get(1), body.getOrder().getLocation().getCoordinates().get(0));
        orderProducts.addAll(body.getOrder().getProducts());
        for (int i = 0; i < orderProducts.size(); i++) {
            orderProducts.get(i).setCount(orderProducts.get(i).getAmount());
        }
        productsAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.app_bar_back, R.id.fragment_add_offer_btn_add_offer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                getActivity().onBackPressed();
                break;
            case R.id.fragment_add_offer_btn_add_offer:
                createAmountArr();
                if (Validation.addOffer(getActivity(),
                        isEmptyOffer,
                        fragmentAddOfferTilTotalPrice)) {
                    if (bananaDelivery) {
                        SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                        dialog.setTitleText(getString(R.string.attention));
                        dialog.setContentText(getString(R.string.delivery_price_message1) + " " + deliveryPrice + " " + getString(R.string.delivery_price_message2));
                        dialog.setConfirmText(getString(R.string.ok));
                        dialog.setCancelText(getString(R.string.cancel));
                        dialog.setConfirmClickListener(sweetAlertDialog -> {
                            sweetAlertDialog.dismiss();
                            price = Double.parseDouble(fragmentAddOfferTilTotalPrice.getEditText().getText().toString()) + deliveryPrice;
                            addOffer();
                        });
                        dialog.setCancelClickListener(Dialog::dismiss);
                        dialog.setCancelable(false);
                        dialog.show();
                    } else {
                        price = Double.parseDouble(fragmentAddOfferTilTotalPrice.getEditText().getText().toString());
                        addOffer();
                    }

                }

                break;

        }
    }

    private void addOffer() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().addOffer(loadDataString(getActivity(), TOKEN),
                    new AddOfferRequestBody(orderId,
                            price,
                            bananaDelivery,
                            amountArr))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();
                            if (response.isSuccessful()) {
                                showSuccessDialogCloseFragment(getActivity(), getString(R.string.done));
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<GeneralResponse> call, Throwable t) {
                            dismissProgressDialog();
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    private void createAmountArr() {
        isEmptyOffer = true;
        amountArr = new ArrayList<>();
        for (OrderProduct product : orderProducts) {
            amountArr.add(new OfferItem(product.getId(), product.getCount()));
            if (product.getCount() != 0)
                isEmptyOffer = false;
        }
    }

}