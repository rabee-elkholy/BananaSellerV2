package com.androdu.bananaSeller.view.fragment.homeCycle.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.DeliveryTabsAdapter;
import com.androdu.bananaSeller.data.local.SharedPreferencesManger;
import com.androdu.bananaSeller.view.activity.HomeActivity;
import com.androdu.bananaSeller.view.activity.SplashActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.FIRST_TIME;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataString;

public class DeliveryFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.app_bar_logout)
    ImageButton appBarLogout;
    @BindView(R.id.fragment_delivery_tl_tabLayout)
    TabLayout fragmentDeliveryTlTabLayout;
    @BindView(R.id.fragment_delivery_vp_view_pager)
    ViewPager2 fragmentDeliveryVpViewPager;
    private View view;

    public DeliveryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delivery, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.my_orders));
        appBarBack.setVisibility(View.GONE);
        appBarLogout.setVisibility(View.VISIBLE);
        DeliveryTabsAdapter deliveryTabsAdapter = new DeliveryTabsAdapter(getActivity());
        fragmentDeliveryVpViewPager.setAdapter(deliveryTabsAdapter);
        fragmentDeliveryTlTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        new TabLayoutMediator(fragmentDeliveryTlTabLayout, fragmentDeliveryVpViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0) {
                            tab.setText(R.string.current);
                        } else if (position == 1) {
                            tab.setText(R.string.delivered);
                        }
                    }
                }).attach();
    }

    @OnClick(R.id.app_bar_logout)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), SplashActivity.class);
        startActivity(intent);
        SharedPreferencesManger.clean(getActivity());
        saveDataString(getActivity(), FIRST_TIME, "sss");
        getActivity().finish();
    }
}