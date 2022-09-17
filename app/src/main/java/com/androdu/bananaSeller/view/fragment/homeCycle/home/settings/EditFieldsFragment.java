package com.androdu.bananaSeller.view.fragment.homeCycle.home.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.EditFieldsAdapter;
import com.androdu.bananaSeller.data.local.SharedPreferencesManger;
import com.androdu.bananaSeller.data.model.Filter;
import com.androdu.bananaSeller.data.model.requestBody.addOffer.FieldRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.data.model.response.SellerFieldsResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.Constants;
import com.androdu.bananaSeller.helper.HelperMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_FIELD;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.ApiErrorHandler.showErrorMessage;
import static com.androdu.bananaSeller.helper.Constants.fieldsList;
import static com.androdu.bananaSeller.helper.Constants.fieldsMap;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

@SuppressLint("NonConstantResourceId")
public class EditFieldsFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_edit_fields_rv_my_fields)
    RecyclerView fragmentEditFieldsRvMyFields;
    @BindView(R.id.fragment_edit_fields_rv_other_fields)
    RecyclerView fragmentEditFieldsRvOtherFields;

    private final List<Filter> myFields = new ArrayList<>();
    private final List<Filter> otherFields = new ArrayList<>();
    private EditFieldsAdapter myFieldsAdapter, otherFieldsAdapter;
    private Filter addedField;

    public EditFieldsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_fields, container, false);
        ButterKnife.bind(this, view);
        init();
        getFields();
        return view;

    }

    private void getFields() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().getFields(loadDataString(getActivity(), TOKEN))
                    .enqueue(new Callback<SellerFieldsResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SellerFieldsResponse> call, @NonNull Response<SellerFieldsResponse> response) {
                            dismissProgressDialog();
                            if (response.isSuccessful()) {
                                onGetFieldsSuccess(response.body().getData());

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                showErrorMessage(requireActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<SellerFieldsResponse> call, @NonNull Throwable t) {
                            dismissProgressDialog();
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @SuppressWarnings("ConstantConditions")
    private void onGetFieldsSuccess(List<String> data) {

        for (String key : fieldsList) {
            if (data.contains(key)) {
                myFields.add(new Filter(getString(fieldsMap.get(key)), fieldsList.indexOf(key)));
            } else {
                otherFields.add(new Filter(getString(fieldsMap.get(key)), fieldsList.indexOf(key)));
            }
        }

        myFieldsAdapter.notifyDataSetChanged();
        otherFieldsAdapter.notifyDataSetChanged();


    }

    private void init() {
        appBarTitle.setText(R.string.edit_my_fields);

        myFieldsAdapter = new EditFieldsAdapter(myFields, 1);

        fragmentEditFieldsRvMyFields.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentEditFieldsRvMyFields.setAdapter(myFieldsAdapter);

        otherFieldsAdapter = new EditFieldsAdapter(otherFields, 2);

        otherFieldsAdapter.SetOnItemClickListener((position, model) -> {
            SweetAlertDialog dialog = new SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE);
            dialog.setTitleText(getString(R.string.are_you_sure));
            dialog.setContentText(getString(R.string.your_licence_will_be_deleted_you_shoud_send_new_one));
            dialog.setConfirmText(getString(R.string.ok));
            dialog.setCancelText(getString(R.string.cancel));
            dialog.setConfirmClickListener(sweetAlertDialog -> {
                addedField = model;
                changeField();
                sweetAlertDialog.dismiss();
            });
            dialog.show();
        });
        fragmentEditFieldsRvOtherFields.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentEditFieldsRvOtherFields.setAdapter(otherFieldsAdapter);


    }

    private void changeField() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            Log.d("debugging", "changeField: " + addedField.getName() + " : " + fieldsList.get(addedField.getKey()));
            getClient().changeField(loadDataString(getActivity(), TOKEN), new FieldRequestBody(fieldsList.get(addedField.getKey())))
                    .enqueue(new Callback<SellerFieldsResponse>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(@NonNull Call<SellerFieldsResponse> call, @NonNull Response<SellerFieldsResponse> response) {
                            dismissProgressDialog();
                            if (response.isSuccessful()) {
                                onChangeSuccess();
                            } else {
                                dismissProgressDialog();
                                Log.d("error_handler", "onResponse: " + response.message());
                                showErrorMessage(requireActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<SellerFieldsResponse> call, @NonNull Throwable t) {
                            dismissProgressDialog();
                            Log.d("error_handler", "onResponse: " + t.getMessage());
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private void onChangeSuccess() {
        otherFields.remove(addedField);
        otherFields.add(myFields.get(0));
        myFields.clear();
        myFields.add(addedField);
        myFieldsAdapter.notifyDataSetChanged();
        otherFieldsAdapter.notifyDataSetChanged();
        SharedPreferencesManger.saveDataString(getActivity(), USER_FIELD, addedField.getName());
        HelperMethod.showSuccessDialog(getActivity(), getString(R.string.your_field_changed_successfully));

    }


    @OnClick(R.id.app_bar_back)
    public void onViewClicked() {
        requireActivity().onBackPressed();
    }
}