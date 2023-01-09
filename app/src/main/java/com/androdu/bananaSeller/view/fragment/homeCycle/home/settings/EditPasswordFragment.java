package com.androdu.bananaSeller.view.fragment.homeCycle.home.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.requestBody.EditPasswordRequestBody;
import com.androdu.bananaSeller.data.model.response.ChangePassResponse;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.androdu.bananaSeller.helper.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_PASSWORD;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.disableView;
import static com.androdu.bananaSeller.helper.HelperMethod.disappearKeypad;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.enableView;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialogCloseFragment;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;
import static com.androdu.bananaSeller.helper.Validation.editPassword;

@SuppressLint("NonConstantResourceId")
public class EditPasswordFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_edit_password_et_old_password)
    EditText fragmentEditPasswordEtOldPassword;
    @BindView(R.id.fragment_edit_password_et_new_password)
    EditText fragmentEditPasswordEtNewPassword;
    @BindView(R.id.fragment_edit_password_et_confirm_password)
    EditText fragmentEditPasswordEtConfirmPassword;
    @BindView(R.id.fragment_edit_password_btn_confirm)
    Button fragmentEditPasswordBtnConfirm;
    private String currentPassword;
    private boolean logout;

    public EditPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_password, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.edit_password));
        currentPassword = loadDataString(getActivity(), USER_PASSWORD);
    }

    @OnClick({R.id.app_bar_back, R.id.fragment_edit_password_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                requireActivity().onBackPressed();

                break;
            case R.id.fragment_edit_password_btn_confirm:
                disappearKeypad(requireActivity());
                if (editPassword(getActivity(),
                        currentPassword,
                        fragmentEditPasswordEtOldPassword,
                        fragmentEditPasswordEtNewPassword,
                        fragmentEditPasswordEtConfirmPassword)) {
                    logout = false;

                    SweetAlertDialog dialog = new SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE);
                    dialog.setTitleText(getString(R.string.logout_from_other_devices));
                    dialog.setCancelText(getString(R.string.no));
                    dialog.setConfirmText(getString(R.string.yes));
                    dialog.setConfirmClickListener(sweetAlertDialog -> {
                        logout = true;
                        sweetAlertDialog.dismiss();
                        changePassword();
                    });
                    dialog.setCancelClickListener(sweetAlertDialog -> {
                        logout = false;
                        sweetAlertDialog.dismiss();
                        changePassword();
                    });
                    dialog.setCancelable(false);
                    dialog.show();

                }
                break;
        }
    }

    private void changePassword() {
        if (isConnected(getContext())) {
            disableView(fragmentEditPasswordBtnConfirm);

            showProgressDialog(getActivity());
            getClient().editPassword(loadDataString(getActivity(), TOKEN), new EditPasswordRequestBody(currentPassword,
                            fragmentEditPasswordEtNewPassword.getText().toString().trim(),
                            fragmentEditPasswordEtNewPassword.getText().toString().trim(),
                            logout))
                    .enqueue(new Callback<ChangePassResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ChangePassResponse> call, @NonNull Response<ChangePassResponse> response) {
                            dismissProgressDialog();
                            enableView(fragmentEditPasswordBtnConfirm);
                            if (response.isSuccessful()) {
                                saveDataString(getActivity(), USER_PASSWORD, fragmentEditPasswordEtNewPassword.getText().toString());
                                if (logout) {
                                    assert response.body() != null;
                                    saveDataString(getActivity(), TOKEN, "hh " + response.body().getData());
                                }
                                showSuccessDialogCloseFragment(getActivity(), getString(R.string.done));
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(requireActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ChangePassResponse> call, @NonNull Throwable t) {
                            dismissProgressDialog();
                            enableView(fragmentEditPasswordBtnConfirm);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });
        }
    }
}