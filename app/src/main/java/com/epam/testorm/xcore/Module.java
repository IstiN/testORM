package com.epam.testorm.xcore;

import android.content.Context;

import com.epam.testorm.xcore.model.XAuthor;
import com.epam.testorm.xcore.model.XContent;
import com.epam.testorm.xcore.model.XMediaAudios;
import com.epam.testorm.xcore.model.XMediaImages;
import com.epam.testorm.xcore.model.XMediaItem;
import com.epam.testorm.xcore.model.XMediaLinks;
import com.epam.testorm.xcore.model.XMediaVideos;
import com.epam.testorm.xcore.model.XNews;

import by.istin.android.xcore.XCoreHelper;
import by.istin.android.xcore.provider.IDBContentProviderSupport;

/**
 * Created by uladzimir_klyshevich on 8/6/15.
 */
public class Module extends XCoreHelper.BaseModule {

    private static final Class<?>[] ENTITIES = new Class<?>[]{
            XContent.class,
            XAuthor.class,
            XNews.class,
            XMediaItem.class,
            XMediaImages.class,
            XMediaAudios.class,
            XMediaLinks.class,
            XMediaVideos.class,
    };

    @Override
    protected void onCreate(Context context) {
        IDBContentProviderSupport dbContentProviderSupport = registerContentProvider(ENTITIES);
        StreamProcessor processor = new StreamProcessor(dbContentProviderSupport);
        registerAppService(processor);
    }
}
