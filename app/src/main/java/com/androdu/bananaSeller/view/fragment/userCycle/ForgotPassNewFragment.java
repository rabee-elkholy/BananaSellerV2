package com.androdu.bananaSeller.view.fragment.userCycle;

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
import com.androdu.bananaSeller.data.model.requestBody.NewPassRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialogCloseFragment;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;


public class ForgotPassNewFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_forgot_pass_new_et_new_password)
    EditText fragmentForgotPassNewEtNewPassword;
    @BindView(R.id.fragment_forgot_pass_new_et_confirm_password)
    EditText fragmentForgotPassNewEtConfirmPassword;
    @BindView(R.id.fragment_forgot_pass_new_btn_confirm)
    Button fragmentForgotPassNewBtnConfirm;
    private View view;
    private String phone, code;

    public ForgotPassNewFragment() {
        // Required empty public constructor
    }

    public ForgotPassNewFragment(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forgot_pass_new, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.add_new_pass));
    }

    @OnClick({R.id.app_bar_back, R.id.fragment_forgot_pass_new_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                getActivity().onBackPressed();
                break;
            case R.id.fragment_forgot_pass_new_btn_confirm:
                if (Validation.newPassword(getActivity(),
                        fragmentForgotPassNewEtNewPassword,
                        fragmentForgotPassNewEtConfirmPassword)) {
                    addNewPass();
                }
                break;
        }
    }

    private void addNewPass() {
        if (isConnected(getContext())) {
            String pass = fragmentForgotPassNewEtNewPassword.getText().toString();
            showProgressDialog(getActivity());
            getClient().forgotPassChangePass(new NewPassRequestBody(phone, code, pass, pass))
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
}