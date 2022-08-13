package com.androdu.bananaSeller.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.view.activity.UserCycleActivity;

import butterknife.ButterKnife;

import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.FIRST_TIME;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataString;

public class SliderAdapter extends PagerAdapter {


    private Activity context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.ic_slider1, R.drawable.ic_slider2, R.drawable.ic_slider3};
    private Integer[] titles = {R.string.slider_title1, R.string.slider_title2, R.string.slider_title3};
    private Integer[] subTitles = {R.string.slider_subtitle1, R.string.slider_subtitle2, R.string.slider_subtitle3};
    private Integer[] spinners = {R.drawable.ic_spinner1, R.drawable.ic_spinner2, R.drawable.ic_spinner3};

    public SliderAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_item, null);
        ButterKnife.bind(view);
        ImageView image = view.findViewById(R.id.slider_item_iv_image);
        TextView skip = view.findViewById(R.id.slider_item_tv_skip);
        TextView title = view.findViewById(R.id.slider_item_tv_title);
        TextView subTitle = view.findViewById(R.id.slider_item_tv_subtitle);
        Button start = view.findViewById(R.id.slider_item_btn_start);
        ImageView spinner = view.findViewById(R.id.slider_item_iv_spinner);

        image.setImageResource(images[position]);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        title.setText(titles[position]);
        subTitle.setText(subTitles[position]);

        if (position == 2) {
            start.setVisibility(View.VISIBLE);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   goToLogin();
                }
            });
        }

        spinner.setImageResource(spinners[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

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
