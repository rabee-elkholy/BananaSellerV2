package com.androdu.bananaSeller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.androdu.bananaSeller.helper.Constants;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.complaints.ComplaintsListFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.myOffers.MyOffersListFragment;

public class ComplaintsTabsAdapter extends FragmentStateAdapter {

    public ComplaintsTabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ComplaintsListFragment(Constants.COMPLAINT_STATUS_BINDING);
        } else if (position == 1) {
            return new ComplaintsListFragment(Constants.COMPLAINT_STATUS_OK);
        } else {
            return new ComplaintsListFragment(Constants.COMPLAINT_STATUS_CANCELED);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
