package com.androdu.bananaSeller.view.fragment.homeCycle.home.aboutApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.androdu.bananaSeller.helper.Constants.INSTAGRAM;
import static com.androdu.bananaSeller.helper.Constants.TELEGRAM;
import static com.androdu.bananaSeller.helper.Constants.WEB_PAGE;
import static com.androdu.bananaSeller.helper.Constants.WHATSAPP;
import static com.androdu.bananaSeller.helper.HelperMethod.replaceFragment;

@SuppressLint("NonConstantResourceId")
public class AboutAppFragment extends Fragment {
    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_about_app_tv_about_us)
    TextView fragmentAboutAppTvAboutUs;
    @BindView(R.id.fragment_about_app_tv_contact_us)
    TextView fragmentAboutAppTvContactUs;
    @BindView(R.id.fragment_about_app_tv_terms_conditions)
    TextView fragmentAboutAppTvTermsConditions;
    @BindView(R.id.fragment_about_app_tv_privacy)
    TextView fragmentAboutAppTvPrivacy;
    @BindView(R.id.fragment_about_app_tv_app_review)
    TextView fragmentAboutAppTvAppReview;
    @BindView(R.id.fragment_about_app_tv_share)
    TextView fragmentAboutAppTvShare;

    public AboutAppFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.about));
    }

    @OnClick({R.id.app_bar_back, R.id.fragment_about_app_tv_about_us, R.id.fragment_about_app_tv_contact_us, R.id.fragment_about_app_tv_terms_conditions, R.id.fragment_about_app_tv_privacy, R.id.fragment_about_app_tv_app_review, R.id.fragment_about_app_tv_share, R.id.fragment_about_app_iv_go_web, R.id.fragment_about_app_iv_go_telegram, R.id.fragment_about_app_iv_go_insta, R.id.fragment_about_app_iv_go_whats})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                requireActivity().onBackPressed();
                break;
            case R.id.fragment_about_app_tv_about_us:
                replaceFragment(getParentFragmentManager(),
                        R.id.activity_second_home_container,
                        new AboutUsFragment(),
                        true);
                break;
            case R.id.fragment_about_app_tv_contact_us:
                replaceFragment(getParentFragmentManager(),
                        R.id.activity_second_home_container,
                        new ContactUsFragment(),
                        true);
                break;
            case R.id.fragment_about_app_tv_terms_conditions:
                replaceFragment(getParentFragmentManager(),
                        R.id.activity_second_home_container,
                        new TermsPrivacyFragment(1),
                        true);
                break;
            case R.id.fragment_about_app_tv_privacy:
                replaceFragment(getParentFragmentManager(),
                        R.id.activity_second_home_container,
                        new TermsPrivacyFragment(2),
                        true);
                break;
            case R.id.fragment_about_app_tv_app_review:
                break;
            case R.id.fragment_about_app_tv_share:
                break;
            case R.id.fragment_about_app_iv_go_telegram:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(TELEGRAM));
                startActivity(i);

                break;
            case R.id.fragment_about_app_iv_go_insta:
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse(INSTAGRAM));
                startActivity(i2);
                break;
            case R.id.fragment_about_app_iv_go_whats:
                Intent i3 = new Intent(Intent.ACTION_VIEW);
                i3.setData(Uri.parse(WHATSAPP));
                startActivity(i3);
                break;
            case R.id.fragment_about_app_iv_go_web:
                Intent i4 = new Intent(Intent.ACTION_VIEW);
                i4.setData(Uri.parse(WEB_PAGE));
                startActivity(i4);
                break;
        }
    }
}