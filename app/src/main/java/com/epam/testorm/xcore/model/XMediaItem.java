package com.epam.testorm.xcore.model;

import android.content.ContentValues;
import android.provider.BaseColumns;


import by.istin.android.xcore.annotations.dbIndex;
import by.istin.android.xcore.annotations.dbLong;
import by.istin.android.xcore.annotations.dbString;
import by.istin.android.xcore.db.IDBConnection;
import by.istin.android.xcore.db.entity.IGenerateID;
import by.istin.android.xcore.db.impl.DBHelper;
import by.istin.android.xcore.source.DataSourceRequest;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class XMediaItem implements BaseColumns, IGenerateID {

    @dbString
    public static String URL = "url";

    @dbString
    public static String TITLE = "title";

    @dbString
    public static String IMAGE = "image";

    @dbString
    public static String ORIGINAL = "original";

    @dbString
    public static String THUMBNAIL = "thumbnail";

    @dbString
    public static String DESCRIPTION = "description";

    @dbLong
    @dbIndex
    public static final String NEWS_URL = DBHelper.getForeignKey(XNews.class);

    @dbLong
    @dbIndex
    public static final String CONTENT_ID = DBHelper.getForeignKey(XContent.class);

    @Override
    public long generateId(DBHelper dbHelper, IDBConnection db, DataSourceRequest dataSourceRequest, ContentValues contentValues) {
        return contentValues.getAsLong(URL);
    }
}
