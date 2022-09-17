package com.androdu.bananaSeller.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.view.activity.UserCycleActivity;

import butterknife.ButterKnife;

import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.FIRST_TIME;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataString;
import static com.androdu.bananaSeller.helper.Constants.WELCOME_IMAGES;
import static com.androdu.bananaSeller.helper.Constants.WELCOME_SPINNERS;
import static com.androdu.bananaSeller.helper.Constants.WELCOME_SUBTITLES;
import static com.androdu.bananaSeller.helper.Constants.WELCOME_TITLES;

public class SliderAdapter extends PagerAdapter {


    private final Activity context;

    public SliderAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return WELCOME_IMAGES.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.slider_item, null);
        ButterKnife.bind(view);
        ImageView image = view.findViewById(R.id.slider_item_iv_image);
        TextView skip = view.findViewById(R.id.slider_item_tv_skip);
        TextView title = view.findViewById(R.id.slider_item_tv_title);
        TextView subTitle = view.findViewById(R.id.slider_item_tv_subtitle);
        Button start = view.findViewById(R.id.slider_item_btn_start);
        ImageView spinner = view.findViewById(R.id.slider_item_iv_spinner);

        image.setImageResource(WELCOME_IMAGES[position]);
        skip.setOnClickListener(v -> goToLogin());

        title.setText(WELCOME_TITLES[position]);
        subTitle.setText(WELCOME_SUBTITLES[position]);

        if (position == 2) {
            start.setVisibility(View.VISIBLE);
            start.setOnClickListener(v -> goToLogin());
        }

        spinner.setImageResource(WELCOME_SPINNERS[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

    private void goToLogin() {
        saveDataString(context, FIRST_TIME, "sss");
        Intent intent = new Intent(context, UserCycleActivity.class);
        context.startActivity(intent);
        context.finish();
    }
}
