package com.spiwer.androidstandard.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

@SuppressLint("StaticFieldLeak")
public class ContextUtil extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
