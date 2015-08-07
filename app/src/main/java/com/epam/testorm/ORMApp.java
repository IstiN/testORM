package com.epam.testorm;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by Mikhail_Ivanou on 8/7/2015.
 */
public class ORMApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.ORM.equals("sugar")) {
            SugarContext.init(this);
        }
    }
}
