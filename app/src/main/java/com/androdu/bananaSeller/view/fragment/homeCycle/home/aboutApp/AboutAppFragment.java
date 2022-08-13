package com.androdu.bananaSeller.view.fragment.homeCycle.home.aboutApp;

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

import static com.androdu.bananaSeller.helper.HelperMethod.replaceFragment;

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
    private View view;

    public AboutAppFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about_app, container, false);
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
                getActivity().onBackPressed();
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
                String url = "https:t.me/banana_app";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

                break;
            case R.id.fragment_about_app_iv_go_insta:
                String url2 = "https://www.instagram.com/banana.uae?r=nametag";
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse(url2));
                startActivity(i2);
                break;
            case R.id.fragment_about_app_iv_go_whats:
                String url3 = "https://api.whatsapp.com/send?phone=971566151716";
                Intent i3 = new Intent(Intent.ACTION_VIEW);
                i3.setData(Uri.parse(url3));
                startActivity(i3);
                break;
            case R.id.fragment_about_app_iv_go_web:
                String url4 = "http://bananas.ae/";
                Intent i4 = new Intent(Intent.ACTION_VIEW);
                i4.setData(Uri.parse(url4));
                startActivity(i4);
                break;
        }
    }
}