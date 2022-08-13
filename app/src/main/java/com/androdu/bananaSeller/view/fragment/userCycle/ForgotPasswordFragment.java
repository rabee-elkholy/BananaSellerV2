package com.androdu.bananaSeller.view.fragment.userCycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.requestBody.EditMobileRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.view.activity.SecondHomeActivity;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;


public class ForgotPasswordFragment extends Fragment {


    @BindView(R.id.fragment_forgot_password_til_phone)
    TextInputLayout fragmentForgotPasswordEtPhone;
    @BindView(R.id.fragment_forgot_password_btn_confirm)
    Button fragmentForgotPasswordBtnConfirm;

    private View view;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @OnClick({R.id.fragment_forgot_password_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_forgot_password_btn_confirm:
                fragmentForgotPasswordEtPhone.setError(null);

                if (!fragmentForgotPasswordEtPhone.getEditText().getText().toString().isEmpty()) {
                    String phoneNum = fragmentForgotPasswordEtPhone.getEditText().getText().toString().trim();

                    //Setting message manually and performing action on button click
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme)
                            .setTitle(getString(R.string.We_well_send_code_to_this_phone_number))
                            .setMessage(phoneNum + getString(R.string.this_or_edit))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                                dialog.dismiss();
                                forgotPassVerification(phoneNum);
                            })
                            .setNegativeButton(getString(R.string.edit), (dialog, which) -> dialog.dismiss());
                    //Creating dialog box
                    AlertDialog alert = alertDialogBuilder.create();
                    alert.show();

                } else {
                    fragmentForgotPasswordEtPhone.setError(getString(R.string.enter_phone));
                }
                break;
        }
    }

    private void forgotPassVerification(String phone) {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().forgotPassVerification(loadDataString(getActivity(), TOKEN), new EditMobileRequestBody(phone))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();

                            if (response.isSuccessful()) {
                                Intent intent = new Intent(getContext(), SecondHomeActivity.class);
                                intent.putExtra("id", 10);
                                intent.putExtra("type", 2);
                                intent.putExtra("phone", phone);
                                intent.putExtra("code", response.body().getCode());
                                startActivity(intent);
                                getActivity().onBackPressed();
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

}