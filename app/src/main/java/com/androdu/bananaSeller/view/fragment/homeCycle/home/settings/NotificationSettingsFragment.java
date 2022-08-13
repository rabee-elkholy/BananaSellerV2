package com.androdu.bananaSeller.view.fragment.homeCycle.home.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.requestBody.NotificationSettingsRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.data.model.response.notificationSettings.NotificationSettingsResponse;
import com.androdu.bananaSeller.data.model.response.notificationSettings.SendNotfication;
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
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialog;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class NotificationSettingsFragment extends Fragment {


    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_settings_sw_all_notifications)
    SwitchCompat fragmentSettingsSwAllNotifications;
    @BindView(R.id.fragment_settings_sw_nearly_orders)
    SwitchCompat fragmentSettingsSwNearlyOrders;
    @BindView(R.id.fragment_settings_sw_issues)
    SwitchCompat fragmentSettingsSwIssues;
    @BindView(R.id.fragment_settings_sw_orders_status)
    SwitchCompat fragmentSettingsSwOrdersStatus;
    @BindView(R.id.fragment_settings_sw_updates)
    SwitchCompat fragmentSettingsSwUpdates;

    private View view;

    public NotificationSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification_settings, container, false);
        ButterKnife.bind(this, view);
        init();
        getNotificationSettings();
        return view;
    }

    private void init() {
        appBarTitle.setText(R.string.notification_settings);
    }

    @OnClick({R.id.app_bar_back, R.id.fragment_settings_btn_add_balance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                getActivity().onBackPressed();
                break;

            case R.id.fragment_settings_btn_add_balance:
                notificationSettings();
                break;

        }
    }

    private void notificationSettings() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().notificationSettings(loadDataString(getActivity(), TOKEN),
                    new NotificationSettingsRequestBody(fragmentSettingsSwAllNotifications.isChecked(),
                            fragmentSettingsSwNearlyOrders.isChecked(),
                            fragmentSettingsSwIssues.isChecked(),
                            fragmentSettingsSwOrdersStatus.isChecked(),
                            fragmentSettingsSwUpdates.isChecked()))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();

                            if (response.isSuccessful()) {
                                showSuccessDialog(getActivity(), getString(R.string.done));
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

                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    private void getNotificationSettings() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().getNotificationSettings(loadDataString(getActivity(), TOKEN))
                    .enqueue(new Callback<NotificationSettingsResponse>() {
                        @Override
                        public void onResponse(Call<NotificationSettingsResponse> call, Response<NotificationSettingsResponse> response) {
                            dismissProgressDialog();

                            if (response.isSuccessful()) {
                                SendNotfication sendNotfication = response.body().getData().getSendNotfication();
                                fragmentSettingsSwAllNotifications.setChecked(sendNotfication.getAll());
                                fragmentSettingsSwNearlyOrders.setChecked(sendNotfication.getNearOrders());
                                fragmentSettingsSwIssues.setChecked(sendNotfication.getIssues());
                                fragmentSettingsSwOrdersStatus.setChecked(sendNotfication.getOrderStatus());
                                fragmentSettingsSwUpdates.setChecked(sendNotfication.getUpdate());
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<NotificationSettingsResponse> call, Throwable t) {
                            dismissProgressDialog();
                            Log.d("error_handler", "onFailure: " + t.getMessage());
                            t.printStackTrace();

                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });
        }
    }
}