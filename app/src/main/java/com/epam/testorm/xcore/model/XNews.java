package com.epam.testorm.xcore.model;


import android.content.ContentValues;
import android.provider.BaseColumns;

import by.istin.android.xcore.annotations.dbEntity;
import by.istin.android.xcore.annotations.dbLong;
import by.istin.android.xcore.db.IDBConnection;
import by.istin.android.xcore.db.entity.IGenerateID;
import by.istin.android.xcore.db.impl.DBHelper;
import by.istin.android.xcore.source.DataSourceRequest;
import by.istin.android.xcore.utils.HashUtils;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class XNews implements BaseColumns, IGenerateID {

    @dbEntity(clazz = XAuthor.class)
    public static String AUTHOR = "author";

    @dbEntity(clazz = XContent.class)
    public static String CONTENT = "content";

    @dbLong
    public static String TIMESTAMP = "time";

//    @dbEntity(clazz = XMediaItem.class)
//    public static String URL = "url";

    @Override
    public long generateId(DBHelper dbHelper, IDBConnection db, DataSourceRequest dataSourceRequest, ContentValues contentValues) {
        return HashUtils.generateId(contentValues.getAsLong(TIMESTAMP));
    }
}
