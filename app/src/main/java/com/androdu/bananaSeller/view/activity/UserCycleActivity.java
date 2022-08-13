package com.androdu.bananaSeller.view.activity;

import android.os.Bundle;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.helper.HelperMethod;
import com.androdu.bananaSeller.view.fragment.userCycle.LoginFragment;

public class UserCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);

        HelperMethod.replaceFragment(getSupportFragmentManager(),
                R.id.activity_user_cycle_container,
                new LoginFragment(),
                false);
    }
}