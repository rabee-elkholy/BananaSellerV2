package com.androdu.bananaSeller.view.fragment.homeCycle.home.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.view.fragment.BottomSheetFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.androdu.bananaSeller.helper.HelperMethod.replaceFragment;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ARABIC;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ENGLISH;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;

@SuppressLint("NonConstantResourceId")
public class SettingsFragment extends Fragment {


    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_settings_tv_edit_mobile)
    TextView fragmentSettingsTvEditMobile;
    @BindView(R.id.fragment_settings_tv_edit_name)
    TextView fragmentSettingsTvEditName;
    @BindView(R.id.fragment_settings_tv_edit_password)
    TextView fragmentSettingsTvEditPassword;
    @BindView(R.id.fragment_settings_tv_languages)
    TextView fragmentSettingsTvLanguages;
    @BindView(R.id.fragment_settings_tv_edit_activities)
    TextView fragmentSettingsTvEditActivities;
    @BindView(R.id.fragment_settings_tv_my_license)
    TextView fragmentSettingsTvMyLicense;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.settings));
        if (getLanguagePref(getContext()).equals(LANGUAGE_KEY_ENGLISH))
            fragmentSettingsTvLanguages.setText(getString(R.string.english));
        else if (getLanguagePref(getContext()).equals(LANGUAGE_KEY_ARABIC))
            fragmentSettingsTvLanguages.setText(getString(R.string.arabic));
        else
            fragmentSettingsTvLanguages.setText(getString(R.string.urdu));


    }

    @OnClick({R.id.app_bar_back, R.id.fragment_settings_tv_my_license, R.id.fragment_settings_tv_edit_activities, R.id.fragment_settings_tv_edit_mobile, R.id.fragment_settings_tv_edit_name, R.id.fragment_settings_tv_edit_password, R.id.fragment_settings_tv_languages})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                requireActivity().onBackPressed();
                break;
            case R.id.fragment_settings_tv_edit_mobile:
                replaceFragment(getParentFragmentManager(),
                        R.id.activity_second_home_container,
                        new EditMobileFragment(),
                        true);
                break;
            case R.id.fragment_settings_tv_edit_name:
                replaceFragment(getParentFragmentManager(),
                        R.id.activity_second_home_container,
                        new EditNameFragment(),
                        true);
                break;
            case R.id.fragment_settings_tv_edit_password:
                replaceFragment(getParentFragmentManager(),
                        R.id.activity_second_home_container,
                        new EditPasswordFragment(),
                        true);
                break;
            case R.id.fragment_settings_tv_my_license:
                replaceFragment(getParentFragmentManager(),
                        R.id.activity_second_home_container,
                        new SellerInfoFragment(),
                        true);
                break;
            case R.id.fragment_settings_tv_edit_activities:
                replaceFragment(getParentFragmentManager(),
                        R.id.activity_second_home_container,
                        new EditFieldsFragment(),
                        true);
                break;
            case R.id.fragment_settings_tv_languages:
                BottomSheetFragment bottomSheetDialog = new BottomSheetFragment(1);
                bottomSheetDialog.show(getParentFragmentManager(), "Custom Bottom Sheet");
                break;
        }
    }
}