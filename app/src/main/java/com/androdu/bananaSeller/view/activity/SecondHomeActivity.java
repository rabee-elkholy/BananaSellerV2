package com.androdu.bananaSeller.view.activity;

import static com.androdu.bananaSeller.helper.HelperMethod.replaceFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.helper.HelperMethod;
import com.androdu.bananaSeller.view.fragment.homeCycle.MapsFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.delivery.DeliveryFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.AddOfferFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.OrdersFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.PaymentFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.ProductsFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.PullBalanceFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.aboutApp.AboutAppFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.aboutApp.AboutUsFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.aboutApp.TermsPrivacyFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.complaints.ComplaintsFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.myOffers.MyOffersFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.settings.NotificationSettingsFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.settings.SettingsFragment;
import com.androdu.bananaSeller.view.fragment.userCycle.PinTestFragment;
import com.google.android.gms.maps.model.LatLng;

public class SecondHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_home);

        Fragment fragment;

        switch (getIntent().getIntExtra("id", 0)) {
            case 1:
                fragment = new MyOffersFragment();
                break;
            case 2:
                fragment = new OrdersFragment();
                break;
            case 3:
                fragment = new MapsFragment(true, new LatLng(getIntent().getDoubleExtra("lat", 1), getIntent().getDoubleExtra("lng", 1)));
                break;
            case 4:
                fragment = new AddOfferFragment(getIntent().getStringExtra("orderId"), getIntent().getDoubleExtra("delivery_price", 0d));
                break;
            case 5:
                fragment = new SettingsFragment();
                break;
            case 6:
                fragment = new AboutAppFragment();
                break;
            case 7:
                fragment = new ComplaintsFragment();
                break;
            case 8:
                fragment = new MapsFragment();
                break;
            case 9:
                fragment = new PaymentFragment(getIntent().getStringExtra("offerId"));
                break;
            case 10:
                fragment = new PinTestFragment(getIntent().getIntExtra("type", 0),
                        getIntent().getStringExtra("code"),
                        getIntent().getStringExtra("phone"));
                break;
            case 11:
                fragment = new NotificationSettingsFragment();
                break;
            case 12:
                fragment = new AboutUsFragment();
                break;
            case 13:
                fragment = new TermsPrivacyFragment(getIntent().getIntExtra("type", 1));
                break;

            case 14:
                fragment = new PullBalanceFragment();
                break;
            case 15:
                fragment = new DeliveryFragment();
                break;
            case 16:
                fragment = new ProductsFragment(getIntent().getStringExtra("category"), getIntent().getIntExtra("type",0));
                break;
            default:
                fragment = new Fragment();
                break;
        }

        replaceFragment(getSupportFragmentManager(),
                R.id.activity_second_home_container,
                fragment,
                false);
    }
}