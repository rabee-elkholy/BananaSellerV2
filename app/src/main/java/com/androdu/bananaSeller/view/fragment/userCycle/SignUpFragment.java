package com.androdu.bananaSeller.view.fragment.userCycle;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.api.ApiService;
import com.androdu.bananaSeller.data.model.Filter;
import com.androdu.bananaSeller.data.model.requestBody.SignUpRequestBody;
import com.androdu.bananaSeller.data.model.response.login.LoginResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.Constants;
import com.androdu.bananaSeller.helper.HelperMethod;
import com.androdu.bananaSeller.helper.Validation;
import com.androdu.bananaSeller.view.activity.SecondHomeActivity;
import com.androdu.bananaSeller.view.fragment.BottomSheetFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.FCM;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_NAME;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_PASSWORD;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_PHONE;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataString;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveUserData;
import static com.androdu.bananaSeller.helper.HelperMethod.disableView;
import static com.androdu.bananaSeller.helper.HelperMethod.disappearKeypad;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.enableView;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_URDU;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class SignUpFragment extends Fragment {

    @BindView(R.id.fragment_sign_up_til_name)
    TextInputLayout fragmentSignUpTilName;
    @BindView(R.id.fragment_sign_up_et_phone)
    TextInputLayout fragmentSignUpTilPhone;
    @BindView(R.id.fragment_sign_up_til_password)
    TextInputLayout fragmentSignUpTilPassword;
    @BindView(R.id.fragment_sign_up_til_confirm_password)
    TextInputLayout fragmentSignUpTilConfirmPassword;
    @BindView(R.id.fragment_sign_up_btn_register)
    Button fragmentSignUpBtnRegister;
    @BindView(R.id.fragment_sign_up_tv_go_login)
    TextView fragmentSignUpTvGoLogin;
    @BindView(R.id.fragment_sign_up_tit_field)
    TextInputEditText fragmentSignUpTitField;
    @BindView(R.id.fragment_sign_up_til_field)
    TextInputLayout fragmentSignUpTilField;
    @BindView(R.id.fragment_sign_up_cb_terms)
    CheckBox fragmentSignUpCbTerms;
    @BindView(R.id.fragment_sign_up_ccp_code)
    CountryCodePicker fragmentSignUpCcpCode;
    @BindView(R.id.fragment_sign_up_et_code)
    TextInputEditText fragmentSignUpEtCode;
    @BindView(R.id.fragment_sign_up_til_email)
    TextInputLayout fragmentSignUpTilEmail;
    private View view;
    private boolean validNum;

    private List<String> myFields = new ArrayList<>();
    private List<Filter> fields = new ArrayList<>();

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);

        init();


        return view;
    }

    private void init() {
        fields.add(new Filter(getString(R.string.fruits_and_vegetables), 0));
        fields.add(new Filter(getString(R.string.meat_and_fish), 1));
        fields.add(new Filter(getString(R.string.baked_goods), 2));
        fields.add(new Filter(getString(R.string.food), 3));

        EditText phoneEt = fragmentSignUpTilPhone.getEditText();
        String countryCode = fragmentSignUpCcpCode.getSelectedCountryCodeWithPlus();
        fragmentSignUpEtCode.setText(countryCode);
//        Log.d("error_", "init: " + countryCode);

        fragmentSignUpCcpCode.registerCarrierNumberEditText(phoneEt);
        fragmentSignUpCcpCode.setPhoneNumberValidityChangeListener(isValidNumber -> {
            validNum = isValidNumber;
            if (validNum) {
                StringBuilder e = new StringBuilder(fragmentSignUpCcpCode.getFormattedFullNumber());
                phoneEt.setText(e.substring(countryCode.length() + 1));
//                Log.d("error_", "init: " + fragmentSignUpCcpCode.getFormattedFullNumber());
            } else {
                phoneEt.setText(phoneEt.getText().toString().replaceAll(" ", ""));
            }
            phoneEt.setSelection(phoneEt.getText().length());
        });

        phoneEt.setText("");
    }

    @OnClick({R.id.fragment_sign_up_tv_terms, R.id.fragment_sign_up_tv_privacy, R.id.fragment_sign_up_btn_register, R.id.fragment_sign_up_tv_go_login, R.id.fragment_sign_up_tit_field})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_sign_up_tit_field:

                BottomSheetFragment bottomSheetDialog = new BottomSheetFragment(7, fields);
                bottomSheetDialog.show(getParentFragmentManager(), "Custom Bottom Sheet");
                bottomSheetDialog.SetOnConfirmClickListener(new BottomSheetFragment.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(List<Filter> sheetFilters) {

                        Log.d("error_", "onConfirm: " + sheetFilters.size());
                        fragmentSignUpTitField.setText("");
                        myFields.clear();
                        for (Filter filter : sheetFilters) {
                            if (filter.isChecked()) {
                                fragmentSignUpTitField.append(filter.getName());
                                myFields.add(Constants.fields[filter.getKey()]);
                            }
                        }
                        fields = sheetFilters;

                    }
                });
                break;
            case R.id.fragment_sign_up_tv_terms:
                Intent intent = new Intent(getContext(), SecondHomeActivity.class);
                intent.putExtra("id", 13);
                intent.putExtra("type", 1);
                startActivity(intent);

                break;
            case R.id.fragment_sign_up_tv_privacy:
                Intent intent2 = new Intent(getContext(), SecondHomeActivity.class);
                intent2.putExtra("id", 13);
                intent2.putExtra("type", 2);
                startActivity(intent2);
                break;
            case R.id.fragment_sign_up_btn_register:
                Log.d("error_",
                        " code: " + fragmentSignUpCcpCode.getSelectedCountryCode()
                                + " phoneWithNum: " + fragmentSignUpCcpCode.getFullNumber()
                );
                if (Validation.signUp(getActivity(),
                        validNum,
                        fragmentSignUpCbTerms.isChecked(),
                        fragmentSignUpTilName,
                        fragmentSignUpTilEmail,
                        fragmentSignUpTilField,
                        fragmentSignUpTilPhone,
                        fragmentSignUpTilPassword,
                        fragmentSignUpTilConfirmPassword)) {
                    disappearKeypad(getActivity());
                    getToken();
                }
                break;
            case R.id.fragment_sign_up_tv_go_login:
                getActivity().onBackPressed();
                break;
        }
    }

    private void signUp(String fcm) {

        if (isConnected(getContext())) {
            String name = fragmentSignUpTilName.getEditText().getText().toString().trim();
            String email = fragmentSignUpTilEmail.getEditText().getText().toString().trim();
            String phone = fragmentSignUpCcpCode.getFullNumber();
            String password = fragmentSignUpTilPassword.getEditText().getText().toString().trim();
            String code = fragmentSignUpCcpCode.getSelectedCountryCode();
            Log.d("error_", "phone: " + phone
                    + " code: " + fragmentSignUpCcpCode.getSelectedCountryCode()
                    + " phoneWithNum: " + fragmentSignUpCcpCode.getFullNumber()
            );

            String locale;
            if (getLanguagePref(getContext()).equals(LANGUAGE_KEY_URDU))
                locale = "urdu";
            else
                locale = getLanguagePref(getContext());


            ApiService.getClient().signUp(new SignUpRequestBody(name, email, phone, code, password, password, fcm, locale, myFields))
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            dismissProgressDialog();
                            enableView(fragmentSignUpBtnRegister);

                            if (response.isSuccessful()) {
                                saveDataString(getActivity(), USER_NAME, name);
                                saveDataString(getActivity(), USER_PHONE, phone);
                                saveDataString(getActivity(), USER_PASSWORD, password);
                                saveDataString(getActivity(), FCM, fcm);
                                saveDataString(getActivity(), TOKEN, "hh " + response.body().getSellerData().getToken());
                                saveUserData(getActivity(), response.body().getSellerData());

                                SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                                dialog.setTitleText(getString(R.string.account_created_successfully));
                                dialog.setContentText(getString(R.string.activate_your_account_now));
                                dialog.setConfirmText(getString(R.string.ok));
                                dialog.setCancelText(getString(R.string.cancel));
                                dialog.setConfirmClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismiss();
                                    Intent intent = new Intent(getContext(), SecondHomeActivity.class);
                                    intent.putExtra("id", 10);
                                    intent.putExtra("type", 1);
                                    startActivity(intent);
                                    getActivity().onBackPressed();
                                });
                                dialog.setCancelClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismiss();
                                    getActivity().onBackPressed();
                                });
                                dialog.setCancelable(false);
                                dialog.show();
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            dismissProgressDialog();
                            enableView(fragmentSignUpBtnRegister);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });
        } else {
            enableView(fragmentSignUpBtnRegister);
        }
    }

    private void getToken() {
        if (isConnected(getContext())) {
            disableView(fragmentSignUpBtnRegister);
            showProgressDialog(getActivity());
            FirebaseMessaging.getInstance().subscribeToTopic("bananaSeller")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                FirebaseInstanceId.getInstance().getInstanceId()
                                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                if (!task.isSuccessful()) {
                                                    Log.w("login", "getInstanceId failed", task.getException());
                                                    dismissProgressDialog();
                                                    fragmentSignUpBtnRegister.setEnabled(true);
                                                    HelperMethod.showErrorDialog(getActivity(), task.getException().getMessage());

                                                    return;
                                                }

                                                // Get new Instance ID token
                                                String token = task.getResult().getToken();
                                                Log.d("login", "token: " + token);

                                                signUp(token);
                                            }
                                        });
                            } else {
                                fragmentSignUpBtnRegister.setEnabled(true);
                                dismissProgressDialog();
                                HelperMethod.showErrorDialog(getActivity(), task.getException().getMessage());
                            }
                        }
                    });
        }
    }

}