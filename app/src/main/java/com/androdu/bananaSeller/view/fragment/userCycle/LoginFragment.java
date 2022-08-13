package com.androdu.bananaSeller.view.fragment.userCycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.api.ApiService;
import com.androdu.bananaSeller.data.model.requestBody.DeliveryLoginRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.LoginRequestBody;
import com.androdu.bananaSeller.data.model.response.deliveryLogin.DeliveryLoginResponse;
import com.androdu.bananaSeller.data.model.response.login.LoginResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.HelperMethod;
import com.androdu.bananaSeller.helper.Validation;
import com.androdu.bananaSeller.view.activity.HomeActivity;
import com.androdu.bananaSeller.view.activity.SecondHomeActivity;
import com.androdu.bananaSeller.view.fragment.BottomSheetFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hbb20.CCPCountry;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.FCM;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TYPE_DELIVERY;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TYPE_SELLER;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_AVATAR;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_LOGGED;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_NAME;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_PASSWORD;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_PHONE;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_TYPE;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataBoolean;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataInt;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataString;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveUserData;
import static com.androdu.bananaSeller.helper.HelperMethod.disableView;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.enableView;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_URDU;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class LoginFragment extends Fragment {

    @BindView(R.id.fragment_login_lin_lang)
    LinearLayout fragmentLoginLinLang;
    @BindView(R.id.fragment_login_tv_skip)
    TextView fragmentLoginTvSkip;
    @BindView(R.id.fragment_login_til_email_or_phone)
    TextInputLayout fragmentLoginTilEmailOrPhone;
    @BindView(R.id.fragment_login_til_password)
    TextInputLayout fragmentLoginTilPassword;
    @BindView(R.id.fragment_login_tv_forgot_password)
    TextView fragmentLoginTvForgotPassword;
    @BindView(R.id.fragment_login_btn_login)
    Button fragmentLoginBtnLogin;
    @BindView(R.id.fragment_login_tv_go_sign_up)
    TextView fragmentLoginTvGoSignUp;
    @BindView(R.id.fragment_login_rg_user_type)
    RadioGroup fragmentLoginRgUserType;

    private View view;
    private CountryCodePicker ccpCountry;
    private final int SELLER_TYPE = 1;
    private final int DELIVERY_TYPE = 2;
    private int loginType = SELLER_TYPE;
    private boolean validNum;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
//        fragmentLoginRgUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == R.id.fragment_login_rb_seller)
//                    loginType = SELLER_TYPE;
//                else
//                    loginType = DELIVERY_TYPE;
//
//            }
//        });
        EditText phoneOrEmailEt = fragmentLoginTilEmailOrPhone.getEditText();
        ccpCountry = new CountryCodePicker(getContext());
//        ccpCountry.setDefaultCountryUsingNameCode("ae");
        ccpCountry.setCountryForNameCode("eg");
        ccpCountry.registerCarrierNumberEditText(phoneOrEmailEt);
        ccpCountry.setPhoneNumberValidityChangeListener(isValidNumber -> {
            validNum = isValidNumber;
        });
    }

    @OnClick({R.id.fragment_login_tv_go_sign_up, R.id.fragment_login_btn_login, R.id.fragment_login_lin_lang, R.id.fragment_login_tv_skip, R.id.fragment_login_tv_forgot_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_login_lin_lang:
                BottomSheetFragment bottomSheetDialog = new BottomSheetFragment(0);
                bottomSheetDialog.show(getParentFragmentManager(), "Custom Bottom Sheet");
                break;
            case R.id.fragment_login_tv_skip:
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.fragment_login_tv_forgot_password:
                HelperMethod.replaceFragment(getParentFragmentManager(),
                        R.id.activity_user_cycle_container,
                        new ForgotPasswordFragment(),
                        true);
                break;
            case R.id.fragment_login_tv_go_sign_up:
                HelperMethod.replaceFragment(getParentFragmentManager(),
                        R.id.activity_user_cycle_container,
                        new SignUpFragment(),
                        true);
                break;

            case R.id.fragment_login_btn_login:
                Log.d("error_", "onViewClicked: " + ccpCountry.getFullNumber() + validNum);

                if (Validation.login(getActivity(),
                        fragmentLoginTilEmailOrPhone,
                        fragmentLoginTilPassword)) {
                    HelperMethod.disappearKeypad(getActivity());
//                    if (loginType == SELLER_TYPE)
                    getToken();
//                    else
//                        deliveryLogin();

                }
                break;
        }
    }

    private void login(String fcm) {
        if (isConnected(getContext())) {
            String phone;
            if (validNum)
                phone = ccpCountry.getFullNumber();
            else
                phone = fragmentLoginTilEmailOrPhone.getEditText().getText().toString().trim();

            String password = fragmentLoginTilPassword.getEditText().getText().toString().trim();

            String locale;
            if (getLanguagePref(getContext()).equals(LANGUAGE_KEY_URDU))
                locale = "urdu";
            else
                locale = getLanguagePref(getContext());

            ApiService.getClient().login(new LoginRequestBody(phone, password, fcm, locale))
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            dismissProgressDialog();
                            enableView(fragmentLoginBtnLogin);
                            if (response.isSuccessful()) {
                                saveDataBoolean(getActivity(), USER_LOGGED, true);
                                saveDataString(getActivity(), USER_NAME, response.body().getSellerData().getUserName());
                                saveDataString(getActivity(), USER_PHONE, response.body().getSellerData().getUserMobile());
                                saveDataString(getActivity(), USER_TYPE, TYPE_SELLER);
                                saveDataString(getActivity(), USER_PASSWORD, password);
                                saveDataInt(getActivity(), USER_AVATAR, response.body().getSellerData().getSellerImage());
                                saveDataString(getActivity(), TOKEN, "hh " + response.body().getSellerData().getToken());
                                saveDataString(getActivity(), FCM, fcm);
                                saveUserData(getActivity(), response.body().getSellerData());
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            dismissProgressDialog();
                            enableView(fragmentLoginBtnLogin);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });
        } else
            enableView(fragmentLoginBtnLogin);
    }

    private void deliveryLogin() {
        if (isConnected(getContext())) {
            disableView(fragmentLoginBtnLogin);
            showProgressDialog(getActivity());
            String phone = fragmentLoginTilEmailOrPhone.getEditText().getText().toString().trim();
            String password = fragmentLoginTilPassword.getEditText().getText().toString().trim();

            ApiService.getClient().loginDelivery(new DeliveryLoginRequestBody(phone, password))
                    .enqueue(new Callback<DeliveryLoginResponse>() {
                        @Override
                        public void onResponse(Call<DeliveryLoginResponse> call, Response<DeliveryLoginResponse> response) {
                            dismissProgressDialog();
                            enableView(fragmentLoginBtnLogin);
                            if (response.isSuccessful()) {
                                saveDataBoolean(getActivity(), USER_LOGGED, true);
                                saveDataString(getActivity(), USER_NAME, response.body().getData().getDeliveryName());
                                saveDataString(getActivity(), USER_TYPE, TYPE_DELIVERY);
                                saveDataString(getActivity(), USER_PHONE, response.body().getData().getDeliveryMobile());
                                saveDataString(getActivity(), USER_PASSWORD, password);
                                saveDataString(getActivity(), TOKEN, "hh " + response.body().getData().getToken());
                                //open delivery activity
                                Intent intent = new Intent(getActivity(), SecondHomeActivity.class);
                                intent.putExtra("id", 15);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<DeliveryLoginResponse> call, Throwable t) {
                            dismissProgressDialog();
                            enableView(fragmentLoginBtnLogin);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });
        }
    }

    private void getToken() {
        if (isConnected(getContext())) {
            disableView(fragmentLoginBtnLogin);
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
                                                    fragmentLoginBtnLogin.setEnabled(true);
                                                    showErrorDialog(getActivity(), task.getException().getMessage());

                                                    return;
                                                }

                                                // Get new Instance ID token
                                                String token = task.getResult().getToken();
                                                Log.d("login", "token: " + token);

                                                login(token);
                                            }
                                        });
                            } else {
                                fragmentLoginBtnLogin.setEnabled(true);
                                dismissProgressDialog();
                                showErrorDialog(getActivity(), task.getException().getMessage());
                            }
                        }
                    });
        }
    }
}