package com.androdu.bananaSeller.view.fragment.homeCycle.home.myOffers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.OrdersTabsAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class MyOffersFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_orders_tl_tabLayout)
    TabLayout fragmentOrdersTlTabLayout;
    @BindView(R.id.fragment_orders_vp_view_pager)
    ViewPager2 fragmentOrdersVpViewPager;

    public MyOffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_offers, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.my_offers));
        OrdersTabsAdapter ordersTabsAdapter = new OrdersTabsAdapter(getActivity());
        fragmentOrdersVpViewPager.setAdapter(ordersTabsAdapter);
        fragmentOrdersTlTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        new TabLayoutMediator(fragmentOrdersTlTabLayout, fragmentOrdersVpViewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText(R.string.binding);
                    } else if (position == 1) {
                        tab.setText(R.string.current);
                    } else if (position == 2) {
                        tab.setText(R.string.completed);
                    } else {
                        tab.setText(R.string.canceled);
                    }
                }).attach();
    }

    @OnClick(R.id.app_bar_back)
    public void onViewClicked() {
        requireActivity().onBackPressed();
    }
}