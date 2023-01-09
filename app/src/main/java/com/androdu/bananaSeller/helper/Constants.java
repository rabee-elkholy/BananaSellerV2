package com.androdu.bananaSeller.helper;

import android.view.View;

import com.androdu.bananaSeller.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Constants {
    public final static String[] units = {"kg", "g", "grain", "Liter", "Gallon", "drzn", "bag"};
    public final static String[] fields = {"F-V", "F-M", "B", "F"};
    public final static List<String> fieldsList = new ArrayList<String>() {{
        add("F-V");
        add("F-M");
        add("B");
        add("F");
    }};
    public final static HashMap<String, Integer> fieldsMap = new HashMap<String, Integer>() {{
        put("F-V", R.string.fruits_and_vegetables);
        put("F-M", R.string.meat_and_fish);
        put("B", R.string.baked_goods);
        put("F", R.string.food);
    }};

    public final static Integer[] WELCOME_IMAGES = {R.drawable.ic_slider1, R.drawable.ic_slider2, R.drawable.ic_slider3};
    public final static Integer[] WELCOME_TITLES = {R.string.slider_title1, R.string.slider_title2, R.string.slider_title3};
    public final static Integer[] WELCOME_SUBTITLES = {R.string.slider_subtitle1, R.string.slider_subtitle2, R.string.slider_subtitle3};
    public final static Integer[] WELCOME_SPINNERS = {R.drawable.ic_spinner1, R.drawable.ic_spinner2, R.drawable.ic_spinner3};


    public final static String OFFER_STATUS_BINDING = "started";
    public final static String OFFER_STATUS_STARTED = "comming";
    public final static String OFFER_STATUS_ENDED = "ended";
    public final static String OFFER_STATUS_CANCELED = "cancel";
    public final static String COMPLAINT_STATUS_BINDING = "binding";
    public final static String COMPLAINT_STATUS_OK = "ok";
    public final static String COMPLAINT_STATUS_CANCELED = "cancel";

    public static String BASE_URL = "https://api-new.bananas.ae/";
    public final static Integer[] clientAvatars = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6, R.drawable.avatar7};
    public final static Integer[] sellerAvatars = {R.drawable.avatar10,R.drawable.avatar9, R.drawable.avatar11, R.drawable.avatar12,
            R.drawable.avatar13, R.drawable.avatar14, R.drawable.avatar15, R.drawable.avatar16, R.drawable.avatar17, R.drawable.avatar18,
            R.drawable.avatar19, R.drawable.avatar20, R.drawable.avatar21, R.drawable.avatar22, R.drawable.avatar23, R.drawable.avatar24,
            R.drawable.avatar25, R.drawable.avatar26, R.drawable.avatar27, R.drawable.avatar28, R.drawable.avatar29, R.drawable.avatar30,
            R.drawable.avatar31, R.drawable.avatar32, R.drawable.avatar33};


    public final static String TELEGRAM = "https:t.me/banana_app";
    public final static String INSTAGRAM = "https://www.instagram.com/banana.uae?r=nametag";
    public final static String WHATSAPP = "https://api.whatsapp.com/send?phone=971566151716";
    public final static String WEB_PAGE = "http://bananas.ae/";


    public static View.OnTouchListener FOCUS_TOUCH_LISTENER;
}
