package com.androdu.bananaSeller.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.BottomSheetAdapter;
import com.androdu.bananaSeller.adapter.FieldsAdapter;
import com.androdu.bananaSeller.adapter.LanguagesAdapter;
import com.androdu.bananaSeller.adapter.OrdersFilterAdapter;
import com.androdu.bananaSeller.data.model.Filter;
import com.androdu.bananaSeller.data.model.requestBody.ChangeLangRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.view.activity.SplashActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.FCM;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ARABIC;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ENGLISH;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_URDU;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;
import static com.androdu.bananaSeller.helper.LanguageManager.setNewLocale;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    @BindView(R.id.fragment_bottom_sheet_list)
    RecyclerView fragmentBottomSheetList;
    @BindView(R.id.fragment_bottom_sheet_tv_confirm)
    TextView fragmentBottomSheetTvConfirm;
    @BindView(R.id.fragment_bottom_sheet_tv_cancel)
    TextView fragmentBottomSheetTvCancel;
    private View view;
    private int type;
    private List<Filter> primaryFilter;
    private List<Filter> filters;

    private OnConfirmClickListener mConfirmClickListener;
    private OnConfirmClickListener2 mConfirmClickListener2;
    private int lastCheck;

    public BottomSheetFragment(int type) {
        this.type = type;
    }

    public BottomSheetFragment(int type, int lastCheck) {
        this.type = type;
        this.lastCheck = lastCheck;
    }

    public BottomSheetFragment(int type, List<Filter> primaryFilter) {
        this.type = type;
        this.primaryFilter = primaryFilter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        fragmentBottomSheetList.setLayoutManager(new LinearLayoutManager(getContext()));
        filters = new ArrayList<>();

        switch (type) {
            case 0:
                typeLang2();
                break;
            case 1:
                typeLang();
                break;
            case 2:
                type2();
                break;
            case 3:
                type3();
                break;
            case 4:
                type4();
                break;
            case 5:
                type5();
                break;
            case 6:
                typeOrders();
                break;
            case 7:
                typeFields();
                break;
            case 8:
                typeOrders2();
                break;
        }

    }

    private void typeFields() {
        setClick(filters);
    }


    private void type5() {
        if (primaryFilter.size() <= 2) {
            filters.add(new Filter(getString(R.string.fruits), 1));
            filters.add(new Filter(getString(R.string.vegetables), 2));
            filters.add(new Filter(getString(R.string.sandwiches), 4));
            filters.add(new Filter(getString(R.string.pies), 5));
            filters.add(new Filter(getString(R.string.commodity), 7));
            filters.add(new Filter(getString(R.string.frozen), 8));
            filters.add(new Filter(getString(R.string.refrigerated), 9));
            filters.add(new Filter(getString(R.string.canned_food), 10));
            filters.add(new Filter(getString(R.string.goodies_and_sweets), 11));
            filters.add(new Filter(getString(R.string.animal_products), 12));
            filters.add(new Filter(getString(R.string.fish_products), 13));
        }
        setClick2(filters);
    }

    private void type4() {
        if (primaryFilter.size() <= 2) {
            filters.add(new Filter(getString(R.string.animal_products), 12));
            filters.add(new Filter(getString(R.string.fish_products), 13));
        }
        setClick2(filters);

    }

    private void type3() {
        if (primaryFilter.size() <= 2) {
            filters.add(new Filter(getString(R.string.bread), 3));
            filters.add(new Filter(getString(R.string.sandwiches), 4));
            filters.add(new Filter(getString(R.string.pies), 5));
            filters.add(new Filter(getString(R.string.croissant), 6));
        }
        setClick2(filters);

    }

    private void type2() {
        if (primaryFilter.size() <= 2) {
            filters.add(new Filter(getString(R.string.fruits), 1));
            filters.add(new Filter(getString(R.string.vegetables), 2));
        }
        setClick2(filters);
    }

    private void setClick(List<Filter> filters) {
//        primaryFilter.addAll(0, filters);
        Log.d("error_", "setClick: " + primaryFilter.size());
        FieldsAdapter fieldsAdapter = new FieldsAdapter(getActivity(), primaryFilter);
        fragmentBottomSheetList.setAdapter(fieldsAdapter);

        fragmentBottomSheetTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfirmClickListener.onConfirm(primaryFilter);
                dismiss();
            }
        });
    }

    private void setClick2(List<Filter> filters) {
        primaryFilter.addAll(0, filters);
        Log.d("error_", "setClick: " + primaryFilter.size());
        BottomSheetAdapter filtersAdapter = new BottomSheetAdapter(getActivity(), primaryFilter);
        fragmentBottomSheetList.setAdapter(filtersAdapter);

        fragmentBottomSheetTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfirmClickListener.onConfirm(primaryFilter);
                dismiss();
            }
        });
    }


    private void typeLang() {
        List<String> languages = Arrays.asList(getResources().getStringArray(R.array.languages));
        LanguagesAdapter adapter = new LanguagesAdapter(getActivity(), languages);
        fragmentBottomSheetList.setAdapter(adapter);
        List<String> languageKeys = new ArrayList<>();
        languageKeys.add("en");
        languageKeys.add("ar");
        languageKeys.add("ur");

        fragmentBottomSheetTvConfirm.setOnClickListener(v -> {
            Log.d("sheet", "onClick: " + adapter.lastChecked);
            if (!getLanguagePref(getActivity()).equals(languageKeys.get(adapter.lastChecked))) {
                if (adapter.lastChecked == 0) {
                    changeLanguage(LANGUAGE_KEY_ENGLISH);
                } else if (adapter.lastChecked == 1) {
                    changeLanguage(LANGUAGE_KEY_ARABIC);
                } else
                    changeLanguage("urdu");

            }

        });
    }

    private void changeLanguage(String lang) {
        if (isConnected(getContext())) {
            showProgressDialog(getActivity());

            getClient().changeLanguage(loadDataString(getActivity(), TOKEN),
                    new ChangeLangRequestBody(loadDataString(getActivity(), FCM), lang))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();

                            if (response.isSuccessful()) {
                                if (lang.equals("urdu"))
                                    setNewLocale(getContext(), LANGUAGE_KEY_URDU);
                                else
                                    setNewLocale(getContext(), lang);
                                dismiss();
                                Intent intent = new Intent(getActivity(), SplashActivity.class);
                                startActivity(intent);
                                getActivity().finish();
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

    private void typeLang2() {
        List<String> languages = Arrays.asList(getResources().getStringArray(R.array.languages));
        LanguagesAdapter adapter = new LanguagesAdapter(getActivity(), languages);
        fragmentBottomSheetList.setAdapter(adapter);
        List<String> languageKeys = new ArrayList<>();
        languageKeys.add("en");
        languageKeys.add("ar");
        languageKeys.add("ur");

        fragmentBottomSheetTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sheet", "onClick: " + adapter.lastChecked);
                if (!getLanguagePref(getActivity()).equals(languageKeys.get(adapter.lastChecked))) {
                    if (adapter.lastChecked == 0) {
                        setNewLocale(getActivity(), LANGUAGE_KEY_ENGLISH);
                    } else if (adapter.lastChecked == 1) {
                        setNewLocale(getActivity(), LANGUAGE_KEY_ARABIC);
                    } else
                        setNewLocale(getActivity(), LANGUAGE_KEY_URDU);

                    Intent intent = new Intent(getActivity(), SplashActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

                dismiss();
            }
        });
    }


    private void typeOrders() {
        List<String> filters = Arrays.asList(getResources().getStringArray(R.array.order_sort));
        OrdersFilterAdapter adapter = new OrdersFilterAdapter(getActivity(), filters, lastCheck);
        fragmentBottomSheetList.setAdapter(adapter);
        List<Integer> filterKeys = new ArrayList<>();
        filterKeys.add(0);
        filterKeys.add(1);
        filterKeys.add(2);
        filterKeys.add(3);

        fragmentBottomSheetTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sheet", "onClick: " + adapter.lastChecked);
                List<Filter> f = new ArrayList<>();
                f.add(new Filter(filters.get(adapter.lastChecked),
                        filterKeys.get(adapter.lastChecked)));
                mConfirmClickListener.onConfirm(f);

                dismiss();
            }
        });
    }

    private void typeOrders2() {
        String[] filtersArr = getResources().getStringArray(R.array.order_filter);
        if (primaryFilter.isEmpty()) {
            filters.add(new Filter(filtersArr[1], 1));
            filters.add(new Filter(filtersArr[2], 2));
            filters.add(new Filter(filtersArr[3], 3));
        }
        setClick2(filters);
    }


    @OnClick({R.id.fragment_bottom_sheet_tv_confirm, R.id.fragment_bottom_sheet_tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_bottom_sheet_tv_cancel:
                dismiss();
                break;
        }
    }

    public void SetOnConfirmClickListener(final OnConfirmClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
    }

    public void SetOnConfirmClickListener2(final OnConfirmClickListener2 mConfirmClickListener) {
        this.mConfirmClickListener2 = mConfirmClickListener;
    }

    public interface OnConfirmClickListener {
        void onConfirm(List<Filter> filters);
    }

    public interface OnConfirmClickListener2 {
        void onConfirm(int lastChecked);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("debugging", "onDismiss: ");
    }
}