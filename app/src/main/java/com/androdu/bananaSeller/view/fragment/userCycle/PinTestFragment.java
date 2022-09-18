package com.androdu.bananaSeller.view.fragment.userCycle;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.requestBody.ChangePhoneRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.CheckCodeRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.EditMobileRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.ForgotCheckCodeRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.chaos.view.PinView;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_PHONE;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.disableView;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.enableView;
import static com.androdu.bananaSeller.helper.HelperMethod.replaceFragment;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialogCloseFragment;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class PinTestFragment extends Fragment {

    @BindView(R.id.pin_view)
    PinView pinView;
    @BindView(R.id.resend)
    TextView resend;
    @BindView(R.id.timer)
    TextView timer;
    @BindView(R.id.change_phone)
    TextView changePhone;
    private View view;
    private String code;
    private int type;

    private CountDownTimer countDownTimer;
    private AlertDialog alertDialog;
    private boolean validNum;
    private String phone;

    public PinTestFragment() {
        // Required empty public constructor
    }


    public PinTestFragment(int type, String code, String phone) {
        this.type = type;
        this.code = code;
        this.phone = phone;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pin_test, container, false);
        ButterKnife.bind(this, view);

        init();

        Log.d("checkpin", "code: " + code);

        if (type == 1) {
            signUpVerification();
            changePhone.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            startTimer();
        } else {
            editPhoneVerification();
        }


        return view;
    }

    private void init() {

        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4) {
                    code = s.toString();
                    if (type == 1)
                        signUpCheckCode();
                    else if (type == 2)
                        forgotPassCheckCode();
                    else
                        editPhoneCheckCode();

                }
//                } else

            }
        });

    }

    private void startTimer() {
        disableView(resend);
        resend.setTextColor(getResources().getColor(R.color.black));
        countDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(" (" + millisUntilFinished / 1000 + ")");
            }

            public void onFinish() {
                enableView(resend);
                timer.setText("");
                resend.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        };

        countDownTimer.start();
    }

    @OnClick({R.id.resend, R.id.change_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_phone:
                showAddProductDialog();
                break;
            case R.id.resend:

                if (type == 1)
                    signUpVerification();
                else if (type == 2)
                    forgotPassVerification();
                else
                    editPhoneVerification();

                break;
        }
    }

    private void showAddProductDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        final View v = layoutInflater.inflate(R.layout.change_phone_dialog, null);
        alertDialog = new AlertDialog.Builder(requireActivity()).create();
        alertDialog.setCancelable(true);

        final CountryCodePicker codePicker = v.findViewById(R.id.ccp_code);
        final TextInputLayout tilPhone = v.findViewById(R.id.til_phone);
        final Button btnConfirm = v.findViewById(R.id.btn_confirm);

        EditText phoneEt = tilPhone.getEditText();
        String countryCode = codePicker.getSelectedCountryCodeWithPlus();

        codePicker.registerCarrierNumberEditText(phoneEt);
        codePicker.setPhoneNumberValidityChangeListener(isValidNumber -> {
            validNum = isValidNumber;
            if (validNum) {
                StringBuilder e = new StringBuilder(codePicker.getFormattedFullNumber());
                phoneEt.setText(e.substring(countryCode.length() + 1));
            } else {
                phoneEt.setText(phoneEt.getText().toString().replaceAll(" ", ""));
            }
            phoneEt.setSelection(phoneEt.getText().length());
        });

        phoneEt.setText("");

        btnConfirm.setOnClickListener(v1 -> {
            if (validNum) {
                tilPhone.setError("");
                String phone = codePicker.getFullNumber();
                changePhone(phone);
            }else {
                tilPhone.setError(getString(R.string.invalid_phone_number));
            }
        });

        alertDialog.setView(v);
        alertDialog.show();
    }

    private void changePhone(String phone) {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().signUpChangePhone(loadDataString(getActivity(), TOKEN), new ChangePhoneRequestBody(phone))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();

                            if (response.isSuccessful()) {
                                signUpVerification();
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

    private void signUpCheckCode() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().signUpCheckCode(loadDataString(getActivity(), TOKEN), new CheckCodeRequestBody(code))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();

                            if (response.isSuccessful()) {
                                showSuccessDialogCloseFragment(getActivity(), getString(R.string.account_activated));
                            } else {
                                pinView.setText("");
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

    private void signUpVerification() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().signUpVerification(loadDataString(getActivity(), TOKEN))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();

                            if (response.isSuccessful()) {
                                showSuccessDialog(getActivity(), getString(R.string.code_sent));
                                code = response.body().getCode();
                                Log.d("checkpin", "code: " + code);


                                if (alertDialog != null)
                                    alertDialog.dismiss();
                                Log.d("error_handler", "onResponse: " + response.body().getCode());

                                startTimer();
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

    private void forgotPassVerification() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().forgotPassVerification(loadDataString(getActivity(), TOKEN), new EditMobileRequestBody(phone))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();

                            if (response.isSuccessful()) {
                                showSuccessDialog(getActivity(), getString(R.string.code_sent));
                                code = response.body().getCode();
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

    private void forgotPassCheckCode() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().forgotPassCheckCode(loadDataString(getActivity(), TOKEN), new ForgotCheckCodeRequestBody(phone, code))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();

                            if (response.isSuccessful()) {
                                replaceFragment(getParentFragmentManager(),
                                        R.id.activity_second_home_container,
                                        new ForgotPassNewFragment(phone, code),
                                        false);
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                pinView.setText("");
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

    private void editPhoneVerification() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().editMobileVerification(loadDataString(getActivity(), TOKEN))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();

                            if (response.isSuccessful()) {
                                showSuccessDialog(getActivity(), getString(R.string.code_sent));
                                code = response.body().getCode();
                                if (alertDialog != null)
                                    alertDialog.dismiss();
                                Log.d("error_handler", "onResponse: " + response.body().getCode());

                                startTimer();
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

    private void editPhoneCheckCode() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().editMobileNumCheckCode(loadDataString(getActivity(), TOKEN), new CheckCodeRequestBody(code))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();

                            if (response.isSuccessful()) {
                                showSuccessDialogCloseFragment(getActivity(), getString(R.string.done));
                                saveDataString(getActivity(), USER_PHONE, phone);
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                pinView.setText("");
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

    @Override
    public void onStop() {
        super.onStop();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
}