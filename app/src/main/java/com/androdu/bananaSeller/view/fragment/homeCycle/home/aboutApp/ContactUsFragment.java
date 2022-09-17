package com.androdu.bananaSeller.view.fragment.homeCycle.home.aboutApp;

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
import com.androdu.bananaSeller.data.model.requestBody.ContactUsRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.disableView;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.enableView;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialog;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;
import static com.androdu.bananaSeller.helper.Validation.contactUs;

import java.util.Objects;

@SuppressLint("NonConstantResourceId")
public class ContactUsFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_contact_us_et_name)
    EditText fragmentContactUsEtName;
    @BindView(R.id.fragment_contact_us_et_email)
    EditText fragmentContactUsEtEmail;
    @BindView(R.id.fragment_contact_us_et_message)
    EditText fragmentContactUsEtMessage;
    @BindView(R.id.fragment_contact_us_btn_confirm)
    Button fragmentContactUsBtnConfirm;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.contact_us));
    }

    @OnClick({R.id.app_bar_back, R.id.fragment_contact_us_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                requireActivity().onBackPressed();
                break;
            case R.id.fragment_contact_us_btn_confirm:
                if (contactUs(getActivity(),
                        fragmentContactUsEtName,
                        fragmentContactUsEtEmail,
                        fragmentContactUsEtMessage)) {
                    contactUsMsg();
                }
                break;
        }
    }

    private void contactUsMsg() {
        if (isConnected(getContext())) {
            disableView(fragmentContactUsBtnConfirm);
            showProgressDialog(getActivity());
            getClient().contactUs(loadDataString(getActivity(), TOKEN),
                    new ContactUsRequestBody(fragmentContactUsEtName.getText().toString(),
                            fragmentContactUsEtEmail.getText().toString().trim(),
                            fragmentContactUsEtMessage.getText().toString().trim()))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {
                            dismissProgressDialog();
                            enableView(fragmentContactUsBtnConfirm);
                            if (response.isSuccessful()) {
                                showSuccessDialog(getActivity(), getString(R.string.done));
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(requireActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<GeneralResponse> call, Throwable t) {
                            dismissProgressDialog();
                            enableView(fragmentContactUsBtnConfirm);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });
        }
    }
}