package com.androdu.bananaSeller.view.fragment.homeCycle.home.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.requestBody.ChangePhoneRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.view.activity.SecondHomeActivity;
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
import static com.androdu.bananaSeller.helper.HelperMethod.disableView;
import static com.androdu.bananaSeller.helper.HelperMethod.disappearKeypad;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.enableView;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;
import static com.androdu.bananaSeller.helper.Validation.editMobileNumber;

@SuppressLint("NonConstantResourceId")
public class EditMobileFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_edit_mobile_et_old)
    EditText fragmentEditMobileEtOld;
    @BindView(R.id.fragment_edit_mobile_et_new)
    EditText fragmentEditMobileEtNew;
    @BindView(R.id.fragment_edit_mobile_btn_confirm)
    Button fragmentEditMobileBtnConfirm;
    @BindView(R.id.fragment_edit_mobile_ccp_code_new)
    CountryCodePicker fragmentEditMobileCcpCodeNew;
    @BindView(R.id.fragment_edit_mobile_ccp_code_old)
    CountryCodePicker fragmentEditMobileCcpCodeOld;

    private String currentPhoneNum;

    private boolean validNewNum, validOldNum;

    public EditMobileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_mobile, container, false);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.edit_mobile_number));

        currentPhoneNum = loadDataString(getActivity(), USER_PHONE);

        String oldCountryCode = fragmentEditMobileCcpCodeOld.getSelectedCountryCodeWithPlus();
        String newCountryCode = fragmentEditMobileCcpCodeNew.getSelectedCountryCodeWithPlus();

        fragmentEditMobileCcpCodeOld.registerCarrierNumberEditText(fragmentEditMobileEtOld);
        fragmentEditMobileCcpCodeOld.setPhoneNumberValidityChangeListener(isValidNumber -> {
            validOldNum = isValidNumber;
            if (validOldNum) {
                StringBuilder e = new StringBuilder(fragmentEditMobileCcpCodeOld.getFormattedFullNumber());
                fragmentEditMobileEtOld.setText(e.substring(oldCountryCode.length() + 1));
            } else {
                fragmentEditMobileEtOld.setText(fragmentEditMobileEtOld.getText().toString().replaceAll(" ", ""));
            }
            fragmentEditMobileEtOld.setSelection(fragmentEditMobileEtOld.getText().length());

        });
        fragmentEditMobileEtOld.setText("");



        fragmentEditMobileCcpCodeNew.registerCarrierNumberEditText(fragmentEditMobileEtNew);
        fragmentEditMobileCcpCodeNew.setPhoneNumberValidityChangeListener(isValidNumber -> {
            validNewNum = isValidNumber;
            if (validNewNum) {
                StringBuilder e = new StringBuilder(fragmentEditMobileCcpCodeNew.getFormattedFullNumber());
                fragmentEditMobileEtNew.setText(e.substring(newCountryCode.length() + 1));
            } else {
                fragmentEditMobileEtNew.setText(fragmentEditMobileEtNew.getText().toString().replaceAll(" ", ""));
            }
            fragmentEditMobileEtNew.setSelection(fragmentEditMobileEtNew.getText().length());

        });
        fragmentEditMobileEtNew.setText("");

    }

    @OnClick({R.id.app_bar_back, R.id.fragment_edit_mobile_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                requireActivity().onBackPressed();
                break;
            case R.id.fragment_edit_mobile_btn_confirm:
                disappearKeypad(requireActivity());
                if (editMobileNumber(getActivity(),
                        currentPhoneNum,
                        fragmentEditMobileCcpCodeOld.getFullNumber(),
                        validOldNum,
                        validNewNum,
                        fragmentEditMobileEtOld,
                        fragmentEditMobileEtNew)) {

                    String phone = fragmentEditMobileCcpCodeNew.getFullNumber();

                    editMobile(phone);

                }
                break;
        }
    }

    private void editMobile(String phone) {
        if (isConnected(getContext())) {
            disableView(fragmentEditMobileBtnConfirm);

            showProgressDialog(getActivity());
            getClient().profileEditMobileNum(loadDataString(getActivity(), TOKEN), new ChangePhoneRequestBody(phone))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();
                            enableView(fragmentEditMobileBtnConfirm);
                            if (response.isSuccessful()) {
                                onEditSuccess(phone);
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<GeneralResponse> call, Throwable t) {
                            dismissProgressDialog();
                            enableView(fragmentEditMobileBtnConfirm);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });
        }
    }

    private void onEditSuccess(String phone) {
        fragmentEditMobileEtOld.setText("");
        fragmentEditMobileEtNew.setText("");
        Intent intent = new Intent(getContext(), SecondHomeActivity.class);
        intent.putExtra("id", 10);
        intent.putExtra("type", 3);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }
}