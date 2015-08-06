package com.orm;

import com.orm.SugarContext;

import android.app.Application;

public class SugarApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

}
