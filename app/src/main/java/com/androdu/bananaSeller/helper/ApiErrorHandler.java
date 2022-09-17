package com.androdu.bananaSeller.helper;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.view.activity.SecondHomeActivity;
import com.google.gson.Gson;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Response;

public class ApiErrorHandler {

    public static void showErrorMessage(Activity activity, Response response) {
        String[] errorMessages = activity.getResources().getStringArray(R.array.error_handler);
        GeneralResponse errorData;
        Gson gson = new Gson();
        try {
            assert response.errorBody() != null;
            errorData = gson.fromJson(response.errorBody().string(), GeneralResponse.class);
            Log.d("error_handler", "showErrorMessage: " + errorData.getState());

            if (errorData.getState() == 34) {
                SweetAlertDialog dialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
                dialog.setTitleText(errorMessages[errorData.getState()]);
                dialog.setContentText(activity.getString(R.string.activate_your_account_now));
                dialog.setConfirmText(activity.getString(R.string.ok));
                dialog.setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    Intent intent = new Intent(activity, SecondHomeActivity.class);
                    intent.putExtra("id", 10);
                    intent.putExtra("type", 1);
                    activity.startActivity(intent);
                });

                dialog.setCancelable(false);
                dialog.show();
            } else if (errorData.getState() >= errorMessages.length)
                HelperMethod.showErrorDialog(activity, "Unhandled Error! >> " + errorData.getState());
            else
                HelperMethod.showErrorDialog(activity, errorMessages[errorData.getState()]);
        } catch (IOException e) {
            e.printStackTrace();
            HelperMethod.showErrorDialog(activity, activity.getString(R.string.error_occurred_try_again));
        }
    }
}
