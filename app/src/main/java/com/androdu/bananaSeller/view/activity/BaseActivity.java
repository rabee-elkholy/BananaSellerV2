package com.androdu.bananaSeller.view.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;
import static com.androdu.bananaSeller.helper.LanguageManager.setLocale;
import static com.androdu.bananaSeller.helper.LanguageManager.setNewLocale;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context base) {
        try {
            if (getLanguagePref(base).isEmpty())
               super.attachBaseContext(setNewLocale(base, Locale.getDefault().getLanguage()));
           else
               super.attachBaseContext(setLocale(base));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
