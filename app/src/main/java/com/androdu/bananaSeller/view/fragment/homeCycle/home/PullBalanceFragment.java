package com.androdu.bananaSeller.view.fragment.homeCycle.home;

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

import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.requestBody.PullBalanceRequestBody;
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
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.disappearKeypad;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.hideView;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialogCloseFragment;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

@SuppressLint("NonConstantResourceId")
public class PullBalanceFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_payment_et_name)
    EditText fragmentPaymentEtName;
    @BindView(R.id.fragment_payment_et_bank_num)
    EditText fragmentPaymentEtBankNum;
    @BindView(R.id.fragment_payment_et_iban)
    EditText fragmentPaymentEtIban;
    @BindView(R.id.fragment_payment_et_bank_name)
    EditText fragmentPaymentEtBankName;
    @BindView(R.id.fragment_payment_et_balance)
    EditText fragmentPaymentEtBalance;
    @BindView(R.id.fragment_payment_btn_confirm)
    Button fragmentPaymentBtnConfirm;

    public PullBalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pull_balance, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(R.string.withdrawal);
    }

    @OnClick({R.id.app_bar_back, R.id.fragment_payment_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                getActivity().onBackPressed();
                break;
            case R.id.fragment_payment_btn_confirm:
                if (Validation.pullBalance(getActivity(),
                        fragmentPaymentEtName,
                        fragmentPaymentEtBankNum,
                        fragmentPaymentEtIban,
                        fragmentPaymentEtBankName,
                        fragmentPaymentEtBalance)) {
                    disappearKeypad(getActivity());
                    pullBalance();
                }
                break;
        }
    }

    private void pullBalance() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().pullMoney(loadDataString(getActivity(), TOKEN),
                    new PullBalanceRequestBody(Integer.parseInt(fragmentPaymentEtBalance.getText().toString()),
                            fragmentPaymentEtName.getText().toString().trim(),
                            fragmentPaymentEtBankNum.getText().toString().trim(),
                            fragmentPaymentEtIban.getText().toString().trim(),
                            fragmentPaymentEtBankName.getText().toString().trim()))
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

                            Log.d("error_handler", "onFailure: " + t.getMessage());
                            t.printStackTrace();
                        }
                    });

        }
    }
}