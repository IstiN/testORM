package com.epam.testorm.xcore;

import android.app.Activity;
import android.widget.ListAdapter;

import com.epam.testorm.ICacheManager;
import com.epam.testorm.xcore.model.XAuthor;
import com.epam.testorm.xcore.model.XMediaItem;
import com.epam.testorm.xcore.model.XNews;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import by.istin.android.xcore.XCoreHelper;
import by.istin.android.xcore.provider.IDBContentProviderSupport;

/**
 * Created by Mikhail_Ivanou on 7/29/2015.
 */
public class XCoreManager implements ICacheManager{

    private static final Class<?>[] ENTITIES = new Class<?>[]{
            XNews.class,
            XMediaItem.class,
//            XContent.class,
            XAuthor.class,
//            XMediaImages.class,
//            XMediaAudios.class,
//            XMediaLinks.class,
//            XMediaVideos.class
    };

    private Activity mActivity;
    private StreamProcessor processor;

    public XCoreManager(Activity activity) {
        this.mActivity = activity;
        XCoreHelper xCoreHelper = XCoreHelper.get();
        xCoreHelper.onCreate(activity, new ArrayList<Class<? extends XCoreHelper.Module>>(), null);
        IDBContentProviderSupport dbContentProviderSupport = xCoreHelper.registerContentProvider(ENTITIES);
        processor = new StreamProcessor(dbContentProviderSupport);
    }

    @Override
    public void processFeed(String string) {
        try {
            InputStream stream = new ByteArrayInputStream(string.getBytes("UTF_8"));
            processor.execute(null, null, stream);
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
