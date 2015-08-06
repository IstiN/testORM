package com.epam.testorm.xcore.model;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

import by.istin.android.xcore.annotations.dbEntities;
import by.istin.android.xcore.annotations.dbEntity;
import by.istin.android.xcore.annotations.dbIndex;
import by.istin.android.xcore.annotations.dbLong;
import by.istin.android.xcore.annotations.dbString;
import by.istin.android.xcore.db.IDBConnection;
import by.istin.android.xcore.db.entity.IBeforeUpdate;
import by.istin.android.xcore.db.entity.IGenerateID;
import by.istin.android.xcore.db.impl.DBHelper;
import by.istin.android.xcore.source.DataSourceRequest;
import by.istin.android.xcore.utils.HashUtils;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class XContent implements BaseColumns, IBeforeUpdate, IGenerateID {

    @dbString
    public static String DESCRIPTION = "description";

    @dbString
    public static String COMMENT = "comment";

    @dbString
    public static String TITLE = "title";

    @dbEntities(clazz = XMediaImages.class)
    @SerializedName("media:photos")
    public static String PHOTOS = "photos";
//
//    @dbEntities(clazz = XMediaImages.class)
//    @SerializedName("media:links")
//    public static String LINKS = "links";
//
//    @dbEntities(clazz = XMediaImages.class)
//    @SerializedName("media:audios")
//    public static String AUDIOS = "audios";
//
//    @dbEntities(clazz = XMediaImages.class)
//    @SerializedName("media:videos")
//    public static String VIDEOS = "videos";

    @dbLong
    @dbIndex
    public static final String NEWS_REF = DBHelper.getForeignKey(XNews.class);


    @Override
    public long generateId(DBHelper dbHelper, IDBConnection db, DataSourceRequest dataSourceRequest, ContentValues contentValues) {
        return HashUtils.generateId(contentValues.getAsString(DESCRIPTION),contentValues.getAsString(COMMENT),contentValues.getAsString(TITLE));
    }

    @Override
    public void onBeforeUpdate(DBHelper dbHelper, IDBConnection db, DataSourceRequest dataSourceRequest, ContentValues contentValues) {
        if (!contentValues.containsKey(_ID)) {
            contentValues.put(_ID, generateId(dbHelper, db, dataSourceRequest, contentValues));
        }
    }
}
