package com.androdu.bananaSeller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.androdu.bananaSeller.helper.Constants;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.myOffers.MyOffersFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.myOffers.MyOffersListFragment;

public class OrdersTabsAdapter extends FragmentStateAdapter {

    public OrdersTabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new MyOffersListFragment(Constants.OFFER_STATUS_BINDING);
        } else if (position == 1){
            return new MyOffersListFragment(Constants.OFFER_STATUS_STARTED);
        } else if (position == 2){
            return new MyOffersListFragment(Constants.OFFER_STATUS_ENDED);
        } else {
            return new MyOffersListFragment(Constants.OFFER_STATUS_CANCELED);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
