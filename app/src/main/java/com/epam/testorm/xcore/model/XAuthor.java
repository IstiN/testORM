package com.epam.testorm.xcore.model;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

import by.istin.android.xcore.annotations.dbIndex;
import by.istin.android.xcore.annotations.dbLong;
import by.istin.android.xcore.annotations.dbString;
import by.istin.android.xcore.db.IDBConnection;
import by.istin.android.xcore.db.entity.IBeforeUpdate;
import by.istin.android.xcore.db.entity.IGenerateID;
import by.istin.android.xcore.db.impl.DBHelper;
import by.istin.android.xcore.source.DataSourceRequest;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class XAuthor  implements BaseColumns, IBeforeUpdate, IGenerateID {

    @dbString
    public static String NETWORK = "network";

    @dbString
    @SerializedName("id")
    public static String USER_ID = "user_id";

    @dbString
    public static String DISPLAY_NAME = "displayName";

    @dbString
    @SerializedName("avatar:url")
    public static String AVATAR = "avatar";

    @dbString
    @SerializedName("profile:url")
    public static String PROFILE = "profile";

    @dbLong
    public static String REF = "ref";

    @dbLong
    @dbIndex
    public static final String NEWS_URL = DBHelper.getForeignKey(XNews.class);

    @Override
    public long generateId(DBHelper dbHelper, IDBConnection db, DataSourceRequest dataSourceRequest, ContentValues contentValues) {
        return contentValues.getAsLong(REF);
    }

    @Override
    public void onBeforeUpdate(DBHelper dbHelper, IDBConnection db, DataSourceRequest dataSourceRequest, ContentValues contentValues) {
        if (!contentValues.containsKey(_ID)) {
            contentValues.put(_ID, generateId(dbHelper, db, dataSourceRequest, contentValues));
        }
    }
}
