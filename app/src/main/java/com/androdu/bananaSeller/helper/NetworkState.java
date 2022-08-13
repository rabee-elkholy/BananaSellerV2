package com.androdu.bananaSeller.helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.androdu.bananaSeller.R;

import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;

public class NetworkState {
    static ConnectivityManager cm;

    static public boolean isConnected(Context context) {
        try {
            cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (NullPointerException e) {

        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected)
            return true;

        showErrorDialog((Activity) context, context.getString(R.string.no_internet), context.getString(R.string.check_internet));

        return false;
    }
}
