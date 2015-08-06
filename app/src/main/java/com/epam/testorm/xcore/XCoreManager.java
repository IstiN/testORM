package com.epam.testorm.xcore;

import android.app.Activity;
import android.widget.ListAdapter;

import com.epam.testorm.BuildConfig;
import com.epam.testorm.ICacheManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import by.istin.android.xcore.XCoreHelper;
import by.istin.android.xcore.utils.StringUtil;

/**
 * Created by Mikhail_Ivanou on 7/29/2015.
 */
public class XCoreManager implements ICacheManager {

    public XCoreManager(Activity activity) {
        XCoreHelper xCoreHelper = XCoreHelper.get();
        xCoreHelper.onCreate(activity.getApplicationContext(), Arrays.<Class<? extends XCoreHelper.Module>>asList(Module.class), BuildConfig.class);
    }

    @Override
    public void processFeed(String string) {
        try {
            StreamProcessor streamProcessor = (StreamProcessor) XCoreHelper.get().getSystemService(StreamProcessor.APP_SERVICE_KEY);
            InputStream stream = new ByteArrayInputStream(string.getBytes(StringUtil.DEFAULT_ENCODING));
            streamProcessor.execute(null, null, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
