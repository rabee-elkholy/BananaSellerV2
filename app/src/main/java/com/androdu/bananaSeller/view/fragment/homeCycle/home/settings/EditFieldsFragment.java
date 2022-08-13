package com.androdu.bananaSeller.view.fragment.homeCycle.home.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.EditFieldsAdapter;
import com.androdu.bananaSeller.data.model.Filter;
import com.androdu.bananaSeller.data.model.requestBody.addOffer.FieldRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.data.model.response.SellerFieldsResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
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

public class EditFieldsFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_edit_fields_rv_my_fields)
    RecyclerView fragmentEditFieldsRvMyFields;
    @BindView(R.id.fragment_edit_fields_rv_other_fields)
    RecyclerView fragmentEditFieldsRvOtherFields;
    private View view;

    private List<Filter> myFields = new ArrayList<>();
    private List<Filter> otherFields = new ArrayList<>();
    private List<String> myFieldsKeys;
    private EditFieldsAdapter myFieldsAdapter, otherFieldsAdapter;
    private Filter addedField;

    public EditFieldsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_fields, container, false);
        ButterKnife.bind(this, view);
        init();
        getFields();
//        removeField(new Filter("", 1));
//        removeField(new Filter("", 1));
        return view;

    }

    private void getFields() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            getClient().getFields(loadDataString(getActivity(), TOKEN))
                    .enqueue(new Callback<SellerFieldsResponse>() {
                        @Override
                        public void onResponse(Call<SellerFieldsResponse> call, Response<SellerFieldsResponse> response) {
                            dismissProgressDialog();
                            if (response.isSuccessful()) {
                                onGetFieldsSuccess(response.body().getData());

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<SellerFieldsResponse> call, Throwable t) {
                            dismissProgressDialog();
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    private void onGetFieldsSuccess(List<String> data) {
        myFieldsKeys = data;

        for (String key : Constants.fieldsList) {
            if (myFieldsKeys.contains(key)) {
                myFields.add(new Filter(getString(Constants.fieldsMap.get(key)), Constants.fieldsList.indexOf(key)));
            } else {
                otherFields.add(new Filter(getString(Constants.fieldsMap.get(key)), Constants.fieldsList.indexOf(key)));
            }
        }

        myFieldsAdapter.notifyDataSetChanged();
        otherFieldsAdapter.notifyDataSetChanged();


    }

    private void init() {
        appBarTitle.setText(R.string.edit_my_fields);

        myFieldsAdapter = new EditFieldsAdapter(getActivity(), myFields, 1);
//        myFieldsAdapter.SetOnItemClickListener(new EditFieldsAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(int position, Filter model) {
//                SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
//                dialog.setTitleText(getString(R.string.are_you_sure));
//                dialog.setContentText(getString(R.string.your_field_will_be_deleted));
//                dialog.setConfirmText(getString(R.string.ok));
//                dialog.setCancelText(getString(R.string.cancel));
//                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        removeField(model);
//                        sweetAlertDialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//        });
        fragmentEditFieldsRvMyFields.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentEditFieldsRvMyFields.setAdapter(myFieldsAdapter);

        otherFieldsAdapter = new EditFieldsAdapter(getActivity(), otherFields, 2);
        otherFieldsAdapter.SetOnItemClickListener(new EditFieldsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, Filter model) {
                SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                dialog.setTitleText(getString(R.string.are_you_sure));
                dialog.setContentText(getString(R.string.your_licence_will_be_deleted_you_shoud_send_new_one));
                dialog.setConfirmText(getString(R.string.ok));
                dialog.setCancelText(getString(R.string.cancel));
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        addedField = model;
                        addField();
                        sweetAlertDialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        fragmentEditFieldsRvOtherFields.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentEditFieldsRvOtherFields.setAdapter(otherFieldsAdapter);


    }

    private void addField() {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());
            Log.d("debugging", "addField: " + addedField.getName() + " : " + Constants.fieldsList.get(addedField.getKey()));
            getClient().addField(loadDataString(getActivity(), TOKEN), new FieldRequestBody(Constants.fieldsList.get(addedField.getKey())))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            if (response.isSuccessful()) {
                                removeField(myFields.get(0));

                            } else {
                                dismissProgressDialog();
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


    private void removeField(Filter field) {
        if (isConnected(getContext())) {
            Log.d("debugging", "removeField: " + field.getName() + " : " + Constants.fieldsList.get(field.getKey()));

            getClient().removeField(loadDataString(getActivity(), TOKEN), new FieldRequestBody(Constants.fieldsList.get(field.getKey())))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();
                            if (response.isSuccessful()) {
                                otherFields.remove(addedField);
                                otherFields.add(myFields.get(0));
                                myFields.clear();
                                myFields.add(addedField);
                                myFieldsAdapter.notifyDataSetChanged();
                                otherFieldsAdapter.notifyDataSetChanged();
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


    @OnClick(R.id.app_bar_back)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }
}