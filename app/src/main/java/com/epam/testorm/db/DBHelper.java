package com.epam.testorm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;


import com.epam.testorm.db.annotation.dbBoolean;
import com.epam.testorm.db.annotation.dbDouble;
import com.epam.testorm.db.annotation.dbIndex;
import com.epam.testorm.db.annotation.dbInteger;
import com.epam.testorm.db.annotation.dbLong;
import com.epam.testorm.db.annotation.dbString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


/**
 * The Class DBHelper.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "appcache.db";

    public static final String ID_NAME = "_ID";

    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS %1$s";

    private static DBHelper mHelper;

    public DBHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance(Context ctx) {
        if (mHelper == null) {
            mHelper = new DBHelper(ctx);
        }
        return mHelper;
    }

    @Override
    public final void onCreate(final SQLiteDatabase db) {

    }

    @Override
    public final void onUpgrade(final SQLiteDatabase db, final int oldVersion,
                                final int newVersion) {
    }

    public static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS %1$s ("
            + ID_NAME
            + " LONG NOT NULL PRIMARY KEY)";

    public void createTables(Class[] cacheItems) {
        SQLiteDatabase writableDatabase = mHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        for (Class current : cacheItems) {
            String tableName = getTableName(current);
            if (TextUtils.isEmpty(tableName)) {
                continue;
            }
            String sql = String.format(CREATE_TABLE_SQL, tableName);
            writableDatabase.execSQL(sql);
            createTableFields(writableDatabase, tableName, current);

        }
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
    }

    public String getTableName(Class current) {
        try {
            Object instance = current.newInstance();
            if (instance instanceof ICache) {
                return ((ICache) instance).getTableName();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createTableFields(SQLiteDatabase writableDatabase, String tableName, Class cacheItem) {
        Field[] declaredFields = cacheItem.getDeclaredFields();
        for (Field field : declaredFields ) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            if (declaredAnnotations != null && declaredAnnotations.length > 0) {
                String index = " ";
                String type;
                for (Annotation attr : declaredAnnotations) {
                    type = getType(attr);
                    String dbField = field.getName();
                    if (!TextUtils.isEmpty(type)) {
                        try {
                            String sql = "ALTER TABLE %s ADD `" + dbField + "` " + type + " " + index;
                            writableDatabase.execSQL(String.format(sql, tableName));
                        } catch (Exception e) {
                            Log.e("DB", e.getLocalizedMessage());
                        }

                    }
                    if (attr instanceof dbIndex) {
                        try {
                        String sql = "CREATE INDEX " + dbField + "_" + tableName + "_" +  "_index ON "+tableName+"("+ dbField +")";
                        writableDatabase.execSQL(sql);
                        } catch (Exception e) {
                            Log.e("DB", e.getLocalizedMessage());
                        }
                    }
                }
            }
        }
    }

    private String getType(Annotation attr) {
        if (attr instanceof dbBoolean) {
            return " BOOLEAN ";
        }
        if (attr instanceof dbString) {
            return " LONGTEXT ";
        }
        if (attr instanceof dbInteger) {
            return " INTEGER ";
        }
        if (attr instanceof dbLong) {
            return " BIGINT ";
        }
        if (attr instanceof dbDouble) {
            return " DOUBLE ";
        }
        return null;
    }

}
