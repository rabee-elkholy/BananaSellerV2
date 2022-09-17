package com.androdu.bananaSeller.view.fragment.homeCycle.home.complaints;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.ComplaintsAdapter;
import com.androdu.bananaSeller.adapter.ComplaintsTabsAdapter;
import com.androdu.bananaSeller.adapter.OrdersTabsAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@SuppressLint("NonConstantResourceId")
public class ComplaintsFragment extends Fragment {
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_complaints_tl_tabLayout)
    TabLayout fragmentComplaintsTlTabLayout;
    @BindView(R.id.fragment_complaints_vp_view_pager)
    ViewPager2 fragmentComplaintsVpViewPager;

    public ComplaintsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaints, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.complaints));
        ComplaintsTabsAdapter complaintsTabsAdapter = new ComplaintsTabsAdapter(getActivity());
        fragmentComplaintsVpViewPager.setAdapter(complaintsTabsAdapter);
        fragmentComplaintsTlTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        new TabLayoutMediator(fragmentComplaintsTlTabLayout, fragmentComplaintsVpViewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText(R.string.binding);
                    } else if (position == 1) {
                        tab.setText(R.string.accepted);
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