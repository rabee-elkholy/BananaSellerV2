package com.androdu.bananaSeller.view.fragment.homeCycle.home.complaints;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.ImagesAdapter;
import com.androdu.bananaSeller.data.model.requestBody.ComplaintIdRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.data.model.response.complaints.Complaint;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.Constants;

import java.util.ArrayList;

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
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialogCloseFragment;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ARABIC;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ENGLISH;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

@SuppressLint("NonConstantResourceId")
public class ComplaintDetailsFragment extends Fragment {

    @BindView(R.id.fragment_complaints_details_cv_images)
    LinearLayout fragmentComplaintsDetailsCvImages;
    private String type;
    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_complaints_details_tv_offer_num)
    TextView fragmentComplaintsDetailsTvOfferNum;
    @BindView(R.id.fragment_complaints_details_tv_reason)
    TextView fragmentComplaintsDetailsTvReason;
    @BindView(R.id.fragment_complaints_details_tv_requests)
    TextView fragmentComplaintsDetailsTvRequests;
    @BindView(R.id.fragment_complaints_details_rv_images_list)
    RecyclerView fragmentComplaintsDetailsRvImagesList;
    @BindView(R.id.fragment_complaints_details_rg_delivery)
    RadioGroup fragmentComplaintsDetailsRgAcceptRefuse;
    @BindView(R.id.fragment_complaints_details_btn_confirm)
    Button fragmentComplaintsDetailsBtnConfirm;

    private Complaint complaint;
    private boolean accept = true;

    public ComplaintDetailsFragment() {
        // Required empty public constructor
    }

    public ComplaintDetailsFragment(Complaint complaint, String type) {
        this.complaint = complaint;
        this.type = type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint_details, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(R.string.complaint_details);
        if (!type.equals(Constants.COMPLAINT_STATUS_BINDING)) {
            fragmentComplaintsDetailsRgAcceptRefuse.setVisibility(View.GONE);
            fragmentComplaintsDetailsBtnConfirm.setVisibility(View.GONE);
        }

        fragmentComplaintsDetailsTvOfferNum.setText(complaint.getId());

        if (getLanguagePref(getContext()).equals(LANGUAGE_KEY_ENGLISH))
            fragmentComplaintsDetailsTvReason.setText(complaint.getReason().getReasonEn());
        else if (getLanguagePref(getContext()).equals(LANGUAGE_KEY_ARABIC))
            fragmentComplaintsDetailsTvReason.setText(complaint.getReason().getReasonAr());
        else
            fragmentComplaintsDetailsTvReason.setText(complaint.getReason().getReasonUr());

        fragmentComplaintsDetailsTvRequests.setText(complaint.getDemands());

        fragmentComplaintsDetailsRvImagesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        if (complaint.getImageUrl().isEmpty())
            fragmentComplaintsDetailsCvImages.setVisibility(View.GONE);
        else {
            ArrayList<String> paths = new ArrayList<>();
            paths.addAll(complaint.getImageUrl());

            ImagesAdapter adapter = new ImagesAdapter(getActivity(), paths, 1);
            fragmentComplaintsDetailsRvImagesList.setAdapter(adapter);
        }

        fragmentComplaintsDetailsRgAcceptRefuse.setOnCheckedChangeListener((group, checkedId) -> {
            accept = (checkedId == R.id.fragment_add_offer_rb_banana_delivery);
        });

    }

    private void acceptRefuseComplaint() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            if (accept)
                acceptComplaint();
            else
                refuseComplaint();
        }
    }

    private void acceptComplaint() {
        getClient().acceptComplaint(loadDataString(getActivity(), TOKEN), new ComplaintIdRequestBody(complaint.getId()))
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {
                        dismissProgressDialog();
                        if (response.isSuccessful()) {
                            showSuccessDialogCloseFragment(getActivity(), getString(R.string.done));

                        } else {
                            Log.d("error_handler", "onResponse: " + response.message());
                            ApiErrorHandler.showErrorMessage(requireActivity(), response);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {
                        dismissProgressDialog();
                        Log.d("error_handler", "onFailure: " + t.getMessage());
                        t.printStackTrace();

                        showErrorDialog(getActivity(), t.getMessage());
                    }
                });
    }

    private void refuseComplaint() {

        getClient().refuseComplaint(loadDataString(getActivity(), TOKEN), new ComplaintIdRequestBody(complaint.getId()))
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {
                        dismissProgressDialog();
                        if (response.isSuccessful()) {
                            showSuccessDialogCloseFragment(getActivity(), getString(R.string.done));

                        } else {
                            Log.d("error_handler", "onResponse: " + response.message());
                            ApiErrorHandler.showErrorMessage(requireActivity(), response);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {
                        dismissProgressDialog();
                        Log.d("error_handler", "onFailure: " + t.getMessage());
                        t.printStackTrace();

                        showErrorDialog(getActivity(), t.getMessage());
                    }
                });
    }

    @OnClick({R.id.app_bar_back, R.id.fragment_complaints_details_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                requireActivity().onBackPressed();
                break;
            case R.id.fragment_complaints_details_btn_confirm:
                acceptRefuseComplaint();
                break;
        }
    }
}