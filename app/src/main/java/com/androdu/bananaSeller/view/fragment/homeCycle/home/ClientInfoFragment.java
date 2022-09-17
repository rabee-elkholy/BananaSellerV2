package com.androdu.bananaSeller.view.fragment.homeCycle.home;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.ApiErrorHandler.showErrorMessage;
import static com.androdu.bananaSeller.helper.HelperMethod.hideView;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showView;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.response.clientInfo.ClientInfoResponse;
import com.androdu.bananaSeller.data.model.response.clientInfo.Data;
import com.androdu.bananaSeller.data.model.response.sellerInfo.SellerInfoResponse;
import com.androdu.bananaSeller.helper.Constants;
import com.androdu.bananaSeller.view.activity.SecondHomeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("NonConstantResourceId")
public class ClientInfoFragment extends BottomSheetDialogFragment {

    @BindView(R.id.fragment_client_info_tv_receiver_number)
    TextView fragmentClientInfoTvReceiverNum;
    @BindView(R.id.fragment_client_info_tv_address)
    TextView fragmentClientInfoTvAddress;
    @BindView(R.id.fragment_client_info_tv_date)
    TextView fragmentClientInfoTvDate;
    @BindView(R.id.fragment_client_info_cv_maps)
    CardView fragmentClientInfoCvMaps;
    @BindView(R.id.fragment_client_info_pb_progress)
    ProgressBar fragmentClientInfoPbProgress;
    @BindView(R.id.fragment_client_info_tv_name)
    TextView fragmentClientInfoTvName;
    @BindView(R.id.fragment_client_info_civ_image)
    CircleImageView fragmentClientInfoCivImage;
    @BindView(R.id.fragment_client_info_tv_payment)
    TextView fragmentClientInfoTvPayment;
    @BindView(R.id.fragment_client_info_tv_mobile_num)
    TextView fragmentClientInfoTvMobileNum;
    @BindView(R.id.fragment_client_info_lin_time_date)
    LinearLayout fragmentClientInfoLinTimeDate;
    @BindView(R.id.fragment_client_info_tv_work_time)
    TextView fragmentClientInfoTvWorkTime;
    @BindView(R.id.fragment_client_info_lin_work_time)
    LinearLayout fragmentClientInfoLinWorkTime;
    @BindView(R.id.fragment_client_info_lin_payment)
    LinearLayout fragmentClientInfoLinPayment;
    private String id;
    private int type;

    public ClientInfoFragment() {
        // Required empty public constructor
    }

    public ClientInfoFragment(String id, int type) {
        this.id = id;
        this.type = type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_info, container, false);
        ButterKnife.bind(this, view);
        if (type == 1)
            getClientInfo();
        else if (type == 2)
            getClientInfoD();
        else
            getSellerInfoD();


        return view;
    }

    private void getClientInfo() {
        if (isConnected(getContext())) {
            showView(fragmentClientInfoPbProgress);
            getClient().getClientInfo(loadDataString(getActivity(), TOKEN), id)
                    .enqueue(new Callback<ClientInfoResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ClientInfoResponse> call, @NonNull Response<ClientInfoResponse> response) {
                            hideView(fragmentClientInfoPbProgress);
                            if (response.isSuccessful()) {
                                onGetInfoSuccess(response.body().getData());

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                showErrorMessage(requireActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ClientInfoResponse> call, @NonNull Throwable t) {
                            hideView(fragmentClientInfoPbProgress);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    private void getClientInfoD() {
        if (isConnected(getContext())) {
            showView(fragmentClientInfoPbProgress);
            getClient().getClientInfoD(loadDataString(getActivity(), TOKEN), id)
                    .enqueue(new Callback<ClientInfoResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ClientInfoResponse> call, @NonNull Response<ClientInfoResponse> response) {
                            hideView(fragmentClientInfoPbProgress);
                            if (response.isSuccessful()) {
                                onGetInfoSuccess(response.body().getData());

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                showErrorMessage(requireActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<ClientInfoResponse> call, Throwable t) {
                            hideView(fragmentClientInfoPbProgress);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    private void getSellerInfoD() {
        if (isConnected(getContext())) {
            showView(fragmentClientInfoPbProgress);
            getClient().getSellerInfoD(loadDataString(getActivity(), TOKEN), id)
                    .enqueue(new Callback<SellerInfoResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SellerInfoResponse> call, @NonNull Response<SellerInfoResponse> response) {
                            hideView(fragmentClientInfoPbProgress);
                            if (response.isSuccessful()) {
                                onGetInfoSuccess2(response.body().getData());

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                showErrorMessage(requireActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<SellerInfoResponse> call, @NonNull Throwable t) {
                            hideView(fragmentClientInfoPbProgress);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    private void onGetInfoSuccess2(com.androdu.bananaSeller.data.model.response.sellerInfo.Data data) {
        fragmentClientInfoCivImage.setImageResource(Constants.sellerAvatars[data.getSeller().getImage()]);
        fragmentClientInfoTvName.setText(data.getSeller().getName());
        fragmentClientInfoTvReceiverNum.setText(data.getSeller().getMobile());
        fragmentClientInfoTvAddress.setText(data.getSeller().getCertificate().getStringAdress());
        fragmentClientInfoLinPayment.setVisibility(View.GONE);
        fragmentClientInfoLinTimeDate.setVisibility(View.GONE);
        fragmentClientInfoLinWorkTime.setVisibility(View.VISIBLE);


        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(Long.parseLong(data.getSeller().getCertificate().getAvilable().getFrom()));
        fragmentClientInfoTvWorkTime.setText(String.format("%s %s ", getString(R.string.from2), formatter.format(calendar.getTime())));

        calendar.setTimeInMillis(Long.parseLong(data.getSeller().getCertificate().getAvilable().getTo()));
        fragmentClientInfoTvWorkTime.append(getString(R.string.to2) + " " + formatter.format(calendar.getTime()));


        fragmentClientInfoTvName.setOnClickListener(v -> {
            openWhatsApp(data.getSeller().getCode());
        });
        fragmentClientInfoTvReceiverNum.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + data.getSeller().getCode()));
            startActivity(intent);
        });

        fragmentClientInfoCvMaps.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SecondHomeActivity.class);
            intent.putExtra("id", 3);
            intent.putExtra("lat", data.getSeller().getCertificate().getLocation().getCoordinates().get(1));
            intent.putExtra("lng", data.getSeller().getCertificate().getLocation().getCoordinates().get(0));
            startActivity(intent);
        });
    }

    private void onGetInfoSuccess(Data clientData) {
        fragmentClientInfoCivImage.setImageResource(Constants.clientAvatars[clientData.getImage()]);
        fragmentClientInfoTvName.setText(clientData.getName());
        fragmentClientInfoTvReceiverNum.setText(clientData.getMobile());
        fragmentClientInfoTvAddress.setText(clientData.getAdress());
        if (clientData.getPayMathod().equals("cash"))
            fragmentClientInfoTvPayment.setText(R.string.when_recieving);
        else
            fragmentClientInfoTvPayment.setText(R.string.done);

        if (clientData.getDate() == 0)
            fragmentClientInfoTvDate.setText(R.string.direct_delivery);
        else {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(clientData.getDate());
            fragmentClientInfoTvDate.setText(formatter.format(calendar.getTime()));
        }

        fragmentClientInfoTvName.setOnClickListener(v -> {
            openWhatsApp(clientData.getAccountMobile());
        });

        fragmentClientInfoTvReceiverNum.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + clientData.getMobile()));
            startActivity(intent);
        });

        fragmentClientInfoCvMaps.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SecondHomeActivity.class);
            intent.putExtra("id", 3);
            intent.putExtra("lat", clientData.getLocation().getCoordinates().get(1));
            intent.putExtra("lng", clientData.getLocation().getCoordinates().get(0));
            startActivity(intent);
        });

    }

    private void openWhatsApp(String phoneNum) {
        String url = "https://api.whatsapp.com/send?phone=" + phoneNum;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}