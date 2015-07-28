package com.epam.testorm;

import android.content.Context;

import com.epam.testorm.realm.RealmManager;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class CacheFactory {

    public static ICacheManager getManager(Context context) {
        if (BuildConfig.ORM.equals("REALM")) {
            return new RealmManager(context);
        }
        return new RealmManager(context);
    }

}
