package com.chocoyo.labs.app.demo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // fresco init
        Fresco.initialize(this);
    }
}
