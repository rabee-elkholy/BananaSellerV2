package com.androdu.bananaSeller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.androdu.bananaSeller.helper.Constants;
import com.androdu.bananaSeller.view.fragment.homeCycle.delivery.DeliveryListFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.myOffers.MyOffersListFragment;

public class DeliveryTabsAdapter extends FragmentStateAdapter {

    public DeliveryTabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new DeliveryListFragment(1);
        } else if (position == 1){
            return new DeliveryListFragment(2);
        }else
            return new DeliveryListFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
