package com.androdu.bananaSeller.view.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.androdu.bananaSeller.R;
import com.google.android.gms.security.ProviderInstaller;

import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.FIRST_TIME;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TYPE_SELLER;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_LOGGED;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_TYPE;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataBoolean;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;

public class SplashActivity extends BaseActivity {

    private final long SPLASH_TIME = 1000;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH));
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("notification", "Key: " + key + " Value: " + value);
            }
        }

        if (Build.VERSION.SDK_INT <= 20) {
            try {
                ProviderInstaller.installIfNeeded(this);
            } catch (Exception ignored) {
            }
        }


        new Handler().postDelayed(() -> {
            Intent intent;
            if (loadDataString(SplashActivity.this, FIRST_TIME) == null) {
                intent = new Intent(SplashActivity.this, SliderActivity.class);
            } else if (loadDataBoolean(SplashActivity.this, USER_LOGGED)) {
                if (loadDataString(SplashActivity.this, USER_TYPE).equals(TYPE_SELLER))
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                else {
                    intent = new Intent(SplashActivity.this, SecondHomeActivity.class);
                    intent.putExtra("id", 15);
                }
            } else {
                intent = new Intent(SplashActivity.this, UserCycleActivity.class);
            }

            startActivity(intent);
            finish();
        }, SPLASH_TIME);

//        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
//        QRGEncoder qrgEncoder = new QRGEncoder("rabee elkholy", null, QRGContents.Type.TEXT, 256);
//        qrgEncoder.setColorBlack(Color.BLACK);
//        qrgEncoder.setColorWhite(Color.WHITE);
//        try {
//            // Getting QR-Code as Bitmap
//            bitmap = qrgEncoder.getBitmap();
//            // Setting Bitmap to ImageView
//            ImageView imageView = findViewById(R.id.logo);
//            imageView.setImageBitmap(bitmap);
//        } catch (Exception e) {
//            Log.v("TAG", e.toString());
//        }
    }
}