package com.epam.testorm.xcore;

import android.app.Activity;
import android.widget.ListAdapter;

import com.epam.testorm.ICacheManager;

/**
 * Created by Mikhail_Ivanou on 7/29/2015.
 */
public class XCoreManager implements ICacheManager{

    private static final Class<?>[] ENTITIES = new Class<?>[]{

    };

    private Activity mActivity;

    public XCoreManager(Activity activity) {
        this.mActivity = activity;

    }

    @Override
    public void processFeed(String string) {

    }

    @Override
    public ListAdapter getFullAdapter() {
        return null;
    }

    @Override
    public ListAdapter getFilteredAdapter() {
        return null;
    }

    @Override
    public ListAdapter getImagesOnlyAdapter() {
        return null;
    }
}
