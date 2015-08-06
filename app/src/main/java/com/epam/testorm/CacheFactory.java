package com.epam.testorm;

import android.app.Activity;

import com.epam.testorm.db.StreamDetailsManager;
import com.epam.testorm.greenDao.GreenDaoManager;
import com.epam.testorm.ormlite.ORMManager;
import com.epam.testorm.realm.RealmManager;
import com.epam.testorm.sugar.SugarORMManager;
import com.epam.testorm.xcore.XCoreManager;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class CacheFactory {

    public static ICacheManager getManager(Activity context) {
        if (BuildConfig.ORM.equals("realm")) {
            return new RealmManager(context);
        }
        if (BuildConfig.ORM.equals("xcore")) {
            return new XCoreManager(context);
        }
        if (BuildConfig.ORM.equals("sugar")) {
            return new SugarORMManager(context);
        }
        if (BuildConfig.ORM.equals("ORMLite")) {
            return new ORMManager(context);
        }
        if (BuildConfig.ORM.equals("GreenDao")) {
            return new GreenDaoManager(context);
        }
        return new StreamDetailsManager(context);
    }

}
