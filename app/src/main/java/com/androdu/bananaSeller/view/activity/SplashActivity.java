package com.androdu.bananaSeller.view.activity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
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

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {


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
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationChannel channel = new NotificationChannel(channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.enableLights(true);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();

            channel.setSound(alarmSound, audioAttributes);

            notificationManager.createNotificationChannel(channel);
        }
//
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("notification", "Key: " + key + " Value: " + value);
            }
        }

        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        long SPLASH_TIME = 1000;
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

    }
}