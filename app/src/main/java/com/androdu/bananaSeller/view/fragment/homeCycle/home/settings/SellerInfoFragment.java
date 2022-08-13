package com.androdu.bananaSeller.view.fragment.homeCycle.home.settings;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.ImagesAdapter;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.Validation;
import com.androdu.bananaSeller.view.activity.SecondHomeActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.convertFileToMultipart;
import static com.androdu.bananaSeller.helper.HelperMethod.disableView;
import static com.androdu.bananaSeller.helper.HelperMethod.disappearKeypad;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.enableView;
import static com.androdu.bananaSeller.helper.HelperMethod.getRealPathFromURIPath;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialogCloseFragment;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class SellerInfoFragment extends Fragment {

    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_seller_info_rv_images_list)
    RecyclerView fragmentSellerInfoRvImagesList;
    @BindView(R.id.fragment_seller_info_tit_ex_date)
    TextInputEditText fragmentSellerInfoTitExDate;
    @BindView(R.id.fragment_seller_info_til_ex_date)
    TextInputLayout fragmentSellerInfoTilExDate;
    @BindView(R.id.fragment_seller_info_til_address)
    TextInputLayout fragmentSellerInfoTilAddress;
    @BindView(R.id.fragment_seller_info_tit_location)
    TextInputEditText fragmentSellerInfoTitLocation;
    @BindView(R.id.fragment_seller_info_til_location)
    TextInputLayout fragmentSellerInfoTilLocation;
    @BindView(R.id.fragment_seller_info_tit_working_time)
    TextInputEditText fragmentSellerInfoTitWorkingTime;
    @BindView(R.id.fragment_seller_info_til_working_time)
    TextInputLayout fragmentSellerInfoTilWorkingTime;
    @BindView(R.id.fragment_seller_info_btn_confirm)
    Button fragmentSellerInfoBtnConfirm;
    private View view;
    private List<String> paths;
    private ImagesAdapter adapter;

    final Calendar myCalendar = Calendar.getInstance();
    private long exDate;
    private String startTime;
    private String endTime;
    private LatLng location;
    private long startTimeMs;
    private long endTimeMs;

    public SellerInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_seller_info, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(R.string.seller_info);
        fragmentSellerInfoRvImagesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        paths = new ArrayList<>();
        adapter = new ImagesAdapter(getActivity(), paths, 2);
        fragmentSellerInfoRvImagesList.setAdapter(adapter);

    }

    @OnClick({R.id.app_bar_back, R.id.fragment_seller_info_tv_add_image, R.id.fragment_seller_info_tit_ex_date, R.id.fragment_seller_info_tit_location, R.id.fragment_seller_info_tit_working_time, R.id.fragment_seller_info_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                getActivity().onBackPressed();
                break;
            case R.id.fragment_seller_info_tv_add_image:

                ImagePicker.Companion.with(this)
                        .galleryOnly()
                        .compress(265)
                        .start();
                break;
            case R.id.fragment_seller_info_tit_ex_date:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String myFormat = "dd/MM/yyyy";
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                            fragmentSellerInfoTitExDate.setText(sdf.format(myCalendar.getTime()));
                            exDate = myCalendar.getTime().getTime();

                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                break;
            case R.id.fragment_seller_info_tit_location:
                Intent intent = new Intent(getContext(), SecondHomeActivity.class);
                intent.putExtra("id", 8);
                startActivityForResult(intent, 2);
                break;
            case R.id.fragment_seller_info_tit_working_time:
                startTime = "";
                endTime = "";
                pickTime();
                break;
            case R.id.fragment_seller_info_btn_confirm:
                disappearKeypad(getActivity());
                if (Validation.sellerInfo(getActivity(),
                        paths.size(),
                        startTime,
                        endTime,
                        fragmentSellerInfoTilExDate,
                        fragmentSellerInfoTilAddress,
                        fragmentSellerInfoTilLocation,
                        fragmentSellerInfoTilWorkingTime)) {
                    addCertificate();
                }
                break;
        }
    }

    private void addCertificate() {
        if (isConnected(getContext())) {
            disableView(fragmentSellerInfoBtnConfirm);
            showProgressDialog(getActivity());
            MultipartBody.Part[] parts = new MultipartBody.Part[paths.size()];
            for (int i = 0; i < paths.size(); i++) {
                parts[i] = (convertFileToMultipart(paths.get(i), "image"));
                Log.d("addFile", "onViewClicked: " + paths.get(i));
            }
            getClient().addCertificate(loadDataString(getActivity(), TOKEN),
                    exDate,
                    fragmentSellerInfoTilAddress.getEditText().getText().toString().trim(),
                    location.longitude,
                    location.latitude,
                    String.valueOf(startTimeMs),
                    String.valueOf(endTimeMs),
                    parts)
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();
                            enableView(fragmentSellerInfoBtnConfirm);
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
                            enableView(fragmentSellerInfoBtnConfirm);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });
        }
    }


    private void pickTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view2, hourOfDay, minute) -> {
                    myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalendar.set(Calendar.MINUTE, minute);

                    String myFormat1 = "hh:mma";
                    SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat1);
                    if (startTime.isEmpty()) {
                        startTime = sdf2.format(myCalendar.getTime()).toLowerCase();
                        startTimeMs = myCalendar.getTime().getTime();
                        fragmentSellerInfoTitWorkingTime.setText(getString(R.string.from2) + " " + startTime);
                        pickTime();
                    } else {
                        endTime = sdf2.format(myCalendar.getTime()).toLowerCase();
                        endTimeMs = myCalendar.getTime().getTime();
                        fragmentSellerInfoTitWorkingTime.append(" " + getString(R.string.to2) + " " + endTime);
                    }
                },
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),
                false
        );
        if (startTime.isEmpty()) {
            timePickerDialog.setMessage(getString(R.string.working_from));
        } else {
            timePickerDialog.setMessage(getString(R.string.working_to));
        }
        timePickerDialog.show();
        timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
        timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == 2) {
            location = new LatLng(data.getDoubleExtra("lat", 0), data.getDoubleExtra("long", 0));
            fragmentSellerInfoTitLocation.setText(location.latitude + " : " + location.longitude);

        } else if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            paths.add(getRealPathFromURIPath(uri, getActivity()));
            adapter.notifyDataSetChanged();
        } else {
            Log.d("debugging", "onActivityResult: ");
        }
    }
}