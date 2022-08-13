package com.androdu.bananaSeller.data.local;

import android.app.Activity;
import android.content.SharedPreferences;

import com.androdu.bananaSeller.data.model.response.login.SellerData;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesManger {

    private static SharedPreferences sharedPreferences = null;
    public static final String TOKEN = "token";
    public static final String FCM = "fcm";
    public static final String USER_LOGGED = "user_logged";
    public static final String USER_TYPE = "user_type";
    public static final String FIRST_TIME = "first_time";
    public static final String USER_DATA = "user_data";
    public static final String USER_PHONE = "user_phone";
    public static final String USER_AVATAR = "user_avatar";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";


    public static final String TYPE_SELLER = "1";
    public static final String TYPE_DELIVERY = "2";

    private static void setSharedPreferences(Activity activity) {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences(
                    "banana", MODE_PRIVATE);
        }
    }

    public static void saveDataString(Activity activity, String data_Key, String data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(data_Key, data_Value);
            editor.apply();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static void saveDataInt(Activity activity, String data_Key, int data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(data_Key, data_Value);
            editor.apply();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static void saveDataBoolean(Activity activity, String data_Key, boolean data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(data_Key, data_Value);
            editor.apply();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static void saveDataObject(Activity activity, String data_Key, Object data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String StringData = gson.toJson(data_Value);
            editor.putString(data_Key, StringData);
            editor.apply();
        }
    }

    public static void saveUserData(Activity activity, SellerData userData) {

        if (userData.getToken() == null) {
            userData.setToken(loadUserData(activity).getToken());
        }

        saveDataObject(activity, USER_DATA, userData);
    }


    public static String loadDataString(Activity activity, String data_Key) {
        setSharedPreferences(activity);

        return sharedPreferences.getString(data_Key, null);
    }

    public static int LoadDataInt(Activity activity, String data_Key) {
        setSharedPreferences(activity);

        return sharedPreferences.getInt(data_Key, 0);
    }

    public static boolean loadDataBoolean(Activity activity, String data_Key) {
        setSharedPreferences(activity);

        return sharedPreferences.getBoolean(data_Key, false);
    }

    public static SellerData loadUserData(Activity activity) {
        SellerData data = null;

        Gson gson = new Gson();
        data = gson.fromJson(loadDataString(activity, USER_DATA), SellerData.class);

        return data;
    }

    public static void clean(Activity activity) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }

}
