package com.androdu.bananaSeller.view.fragment.homeCycle.home.aboutApp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.response.termsPrivacy.TermsPrivacyResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.helper.ApiErrorHandler.showErrorMessage;
import static com.androdu.bananaSeller.helper.HelperMethod.hideView;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showView;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ARABIC;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ENGLISH;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

@SuppressLint("NonConstantResourceId")
public class TermsPrivacyFragment extends Fragment {

    @BindView(R.id.fragment_orders_pb_progress_bar)
    ProgressBar fragmentOrdersPbProgressBar;
    private int type;
    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_terms_privacy_tv_body)
    TextView fragmentTermsPrivacyTvBody;

    public TermsPrivacyFragment() {
        // Required empty public constructor
    }

    public TermsPrivacyFragment(int type) {
        this.type = type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terms_privacy, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        if (type == 1) {
            appBarTitle.setText(R.string.terms_conditions);
            getTerms();
        } else {
            appBarTitle.setText(R.string.privacy_policy);
            getPrivacy();
        }
    }


    private void getTerms() {
        if (isConnected(getContext())) {
            showView(fragmentOrdersPbProgressBar);
            getClient().getTerms()
                    .enqueue(new Callback<TermsPrivacyResponse>() {
                        @Override
                        public void onResponse(Call<TermsPrivacyResponse> call, Response<TermsPrivacyResponse> response) {
                            hideView(fragmentOrdersPbProgressBar);

                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                if (response.body().getTermsPrivacy() != null)

                                    if (getLanguagePref(getContext()).equals(LANGUAGE_KEY_ENGLISH))
                                        fragmentTermsPrivacyTvBody.setText(response.body().getTermsPrivacy().getEN());
                                    else if (getLanguagePref(getContext()).equals(LANGUAGE_KEY_ARABIC))
                                        fragmentTermsPrivacyTvBody.setText(response.body().getTermsPrivacy().getAR());
                                    else
                                        fragmentTermsPrivacyTvBody.setText(response.body().getTermsPrivacy().getUr());

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                showErrorMessage(requireActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<TermsPrivacyResponse> call, Throwable t) {
                            hideView(fragmentOrdersPbProgressBar);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }

    }

    private void getPrivacy() {
        if (isConnected(getContext())) {
            showView(fragmentOrdersPbProgressBar);
            getClient().getPrivacy()
                    .enqueue(new Callback<TermsPrivacyResponse>() {
                        @Override
                        public void onResponse(Call<TermsPrivacyResponse> call, Response<TermsPrivacyResponse> response) {
                            hideView(fragmentOrdersPbProgressBar);
                            assert response.body() != null;
                            if (response.body().getTermsPrivacy() != null)
                                if (response.isSuccessful()) {
                                    if (getLanguagePref(getContext()).equals(LANGUAGE_KEY_ENGLISH))
                                        fragmentTermsPrivacyTvBody.setText(response.body().getTermsPrivacy().getEN());
                                    else if (getLanguagePref(getContext()).equals(LANGUAGE_KEY_ARABIC))
                                        fragmentTermsPrivacyTvBody.setText(response.body().getTermsPrivacy().getAR());
                                    else
                                        fragmentTermsPrivacyTvBody.setText(response.body().getTermsPrivacy().getUr());
                                } else {
                                    Log.d("error_handler", "onResponse: " + response.message());
                                    showErrorMessage(requireActivity(), response);
                                }
                        }

                        @Override
                        public void onFailure(Call<TermsPrivacyResponse> call, Throwable t) {
                            hideView(fragmentOrdersPbProgressBar);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }

    }
}