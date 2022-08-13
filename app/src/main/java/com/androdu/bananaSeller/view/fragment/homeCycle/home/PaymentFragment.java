package com.androdu.bananaSeller.view.fragment.homeCycle.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.helper.Validation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.androdu.bananaSeller.helper.HelperMethod.disappearKeypad;

public class PaymentFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.app_bar_filter)
    ImageButton appBarFilter;
    @BindView(R.id.fragment_payment_et_name)
    EditText fragmentPaymentEtName;
    @BindView(R.id.fragment_payment_et_phone)
    EditText fragmentPaymentEtPhone;
    @BindView(R.id.fragment_payment_et_address)
    EditText fragmentPaymentEtAddress;
    @BindView(R.id.fragment_payment_et_date_time)
    EditText fragmentPaymentEtDateTime;
    @BindView(R.id.fragment_payment_cb_delivery)
    CheckBox fragmentPaymentCbDelivery;
    @BindView(R.id.fragment_payment_btn_confirm)
    Button fragmentPaymentBtnConfirm;
    private View view;

    private String offerId;
    private String checkoutId;

    final Calendar myCalendar = Calendar.getInstance();

    public PaymentFragment() {
        // Required empty public constructor
    }

    public PaymentFragment(String offerId) {
        // Required empty public constructor
        this.offerId = offerId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.data));
    }

    @OnClick({R.id.app_bar_back, R.id.fragment_payment_et_date_time, R.id.fragment_payment_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                getActivity().onBackPressed();
                break;
            case R.id.fragment_payment_et_date_time:


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String myFormat = "yyyy/M/d";
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                    (view2, hourOfDay, minute) -> {
                                        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        myCalendar.set(Calendar.MINUTE, minute);

                                        String myFormat1 = "HH:mm a";
                                        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat1, Locale.US);

                                        fragmentPaymentEtDateTime.setText(sdf.format(myCalendar.getTime()));
                                        fragmentPaymentEtDateTime.append(" " + sdf2.format(myCalendar.getTime()));

                                    },
                                    myCalendar.get(Calendar.HOUR_OF_DAY),
                                    myCalendar.get(Calendar.MINUTE),
                                    false
                            );
                            timePickerDialog.show();
                            timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                            timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);

                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);


                break;
            case R.id.fragment_payment_btn_confirm:
                if (Validation.paymentData(getActivity(),
                        fragmentPaymentEtName,
                        fragmentPaymentEtPhone,
                        fragmentPaymentEtAddress,
                        fragmentPaymentEtDateTime)) {
                    disappearKeypad(getActivity());
//                    checkout(offerId);
                }
                break;
        }
    }

//
//
//    private void checkout(String offerId) {
//        if (isConnected(getContext())) {
//            showProgressDialog(getActivity());
//            getClient().createCheckout(loadDataString(getActivity(), TOKEN), new CancelOfferRequestBody(offerId))
//                    .enqueue(new Callback<CheckOutResponse>() {
//                        @Override
//                        public void onResponse(Call<CheckOutResponse> call, Response<CheckOutResponse> response) {
//                            dismissProgressDialog();
//
//                            if (response.isSuccessful()) {
//                                Log.d("error_handler", "onResponse: id: " + response.body().getData().getId());
//                                Set<String> paymentBrands = new LinkedHashSet<String>();
//
//                                paymentBrands.add("VISA");
//                                paymentBrands.add("MASTER");
//                                checkoutId = response.body().getData().getId();
//
//                                CheckoutSettings checkoutSettings = new CheckoutSettings(checkoutId, paymentBrands, Connect.ProviderMode.TEST);
//
//// Set shopper result URL
//                                checkoutSettings.setShopperResultUrl("companyname://result");
//
//                                Intent intent = checkoutSettings.createCheckoutActivityIntent(getContext());
//
//                                startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT);
//                            } else {
//                                Log.d("error_handler", "onResponse: " + response.message());
//                                ApiErrorHandler.showErrorMessage(getActivity(), response);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<CheckOutResponse> call, Throwable t) {
//                            dismissProgressDialog();
//                            Log.d("error_handler", "onFailure: " + t.getMessage());
//                            t.printStackTrace();
//
//                            showErrorDialog(getActivity(), t.getMessage());
//                        }
//                    });
//
//        }
//    }
//
//    private void checkPayment(String offerId, String checkoutId) {
//        if (isConnected(getContext())) {
//            showProgressDialog(getActivity());
//            getClient().checkPay(loadDataString(getActivity(), TOKEN),
//                    new CheckPayRequestBody(checkoutId,
//                            offerId,
//                            fragmentPaymentEtName.getText().toString().trim(),
//                            fragmentPaymentEtPhone.getText().toString().trim(),
//                            fragmentPaymentEtAddress.getText().toString().trim(),
//                            myCalendar.getTimeInMillis()))
//                    .enqueue(new Callback<GeneralResponse>() {
//                        @Override
//                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
//                            dismissProgressDialog();
//
//                            if (response.isSuccessful()) {
//                                showSuccessDialog(getActivity(), getString(R.string.done));
//                            } else {
//                                Log.d("error_handler", "onResponse: " + response.message());
//                                ApiErrorHandler.showErrorMessage(getActivity(), response);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<GeneralResponse> call, Throwable t) {
//                            dismissProgressDialog();
//                            Log.d("error_handler", "onFailure: " + t.getMessage());
//                            t.printStackTrace();
//
//                            showErrorDialog(getActivity(), t.getMessage());
//                        }
//                    });
//
//        }
//    }

}