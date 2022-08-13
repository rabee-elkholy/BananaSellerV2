package com.androdu.bananaSeller.view.activity;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.SliderAdapter;

public class SliderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        SliderAdapter viewPagerAdapter = new SliderAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);
    }
}