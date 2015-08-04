package com.epam.testorm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.util.Log;
import android.widget.ListAdapter;

import com.epam.testorm.ICacheManager;

import com.epam.testorm.db.model.Author;
import com.epam.testorm.db.model.Content;
import com.epam.testorm.db.model.Links;
import com.epam.testorm.db.model.MediaAudios;
import com.epam.testorm.db.model.MediaImages;
import com.epam.testorm.db.model.MediaLinks;
import com.epam.testorm.db.model.MediaVideos;
import com.epam.testorm.db.model.News;
import com.epam.testorm.gson.BaseResponse;
import com.epam.testorm.gson.StreamDetails;
import com.google.gson.Gson;

import java.util.List;

public class StreamDetailsManager implements ICacheManager {

    private static final String TAG = StreamDetailsManager.class.getSimpleName();

    private DBHelper mDBHelper;

    private static StreamDetailsManager instance;

    private static Gson gson = new Gson();

    static Class[] tables = new Class[]{
            News.class,
            Author.class,
            Content.class,
            Links.class,
            MediaAudios.class,
            MediaVideos.class,
            MediaImages.class,
            MediaLinks.class,

    };

    public StreamDetailsManager(Context ctx) {
        mDBHelper = DBHelper.getInstance(ctx);
        mDBHelper.createTables(tables);
    }

    public static StreamDetailsManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new StreamDetailsManager(ctx);
        }
        return instance;
    }

    public long addFirstItems(List<StreamDetails> list, long streamId) throws SQLiteException {
        try {
//            SQLiteDatabase db = mDBHelper.getWritableDatabase();
//            db.execSQL(String.format(DBHelper.CREATE_TABLE_SQL, getTableName(streamId),
//                    DBHelper.NEWS));
//            if (list == null || list.isEmpty()) {
//                return -2;
//            }
            long result = -1;
//            if (Build.VERSION.SDK_INT > 10) {
//                db.beginTransactionNonExclusive();
//            } else {
//                db.beginTransaction();
//            }
//            db.delete(getTableName(streamId), "1", null);
//            for (int i = 0; i < list.size(); i++) {
//                StreamDetails item = list.get(i);
//                if (!TextUtils.isEmpty(item.getCalculatedContentTitle()) || !TextUtils.isEmpty(item.getCalculatedContentComment()) || !TextUtils.isEmpty(item.getCalculatedImage()) || !TextUtils.isEmpty(item.getMainLink())) {
//                    result = db.insertWithOnConflict(getTableName(streamId), null, getContentValues(item, streamId), SQLiteDatabase.CONFLICT_REPLACE);
//                    if (result < 0) {
//                        db.endTransaction();
//                        break;
//                    }
//                }
//            }
//            db.setTransactionSuccessful();
//            db.endTransaction();
            return result;
        } catch (SQLiteException e) {
            return -666;

        } catch (Exception ex) {
            return -1;
        }
    }

    public long addListItems(List<StreamDetails> list, long streamId) {
        try {
//            if (list == null || list.isEmpty()) {
//                return -2;
//            }
            long result = -1;
//            SQLiteDatabase db = mDBHelper.getWritableDatabase();
//            String tableName = getTableName(streamId);
//            db.execSQL(String.format(DBHelper.CREATE_TABLE_SQL, tableName,
//                    DBHelper.NEWS));
//            String sql = "ALTER TABLE %s ADD `" + DBHelper.NEWS_AUTHOR_REF_NAME + "` VARCHAR AFTER `" + DBHelper.NEWS_AUTHOR_KEY_NAME + "`";
//            try {
//                db.execSQL(String.format(sql, tableName));
//            } catch (Exception ignored) {
//
//            }
//
//            if (Build.VERSION.SDK_INT > 10) {
//                db.beginTransactionNonExclusive();
//            } else {
//                db.beginTransaction();
//            }
//            for (int i = 0; i < list.size(); i++) {
//                StreamDetails item = list.get(i);
//                if (!TextUtils.isEmpty(item.getCalculatedContentTitle()) || !TextUtils.isEmpty(item.getCalculatedContentComment()) || !TextUtils.isEmpty(item.getCalculatedImage()) || !TextUtils.isEmpty(item.getMainLink())) {
//                    result = db.insertWithOnConflict(tableName, null, getContentValues(item, streamId), SQLiteDatabase.CONFLICT_REPLACE);
//                    if (result < 0) {
//                        db.endTransaction();
//                        break;
//                    }
//                }
//            }
//            db.setTransactionSuccessful();
//            db.endTransaction();
            return result;
        } catch (SQLiteException e) {
            return -666;

        } catch (Exception ex) {
            return -1;
        }
    }

    public void clearCache() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        for (Class table : tables ) {
            db.delete(mDBHelper.getTableName(table), null, null);
        }
    }


    private Cursor getCursor(long streamId, String selection, String[] selArgs, String order, String limit) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Log.d("HL", "id = " + streamId + " db = " + db.toString());
        Cursor cursor = null;
//        try {
//
//            cursor = db.query(getTableName(streamId), new String[]{
//                    DBHelper.ID_NAME,
//                    DBHelper.NEWS_STREAM_ID_NAME,
//                    DBHelper.NEWS_POST_TIME_NAME,
//                    DBHelper.NEWS_NETWORK_NAME,
//                    DBHelper.NEWS_AUTHOR_NAME,
//                    DBHelper.NEWS_LOGO_NAME,
//                    DBHelper.NEWS_COMMENT_NAME,
//                    DBHelper.NEWS_TITLE_NAME,
//                    DBHelper.NEWS_IMAGE_NAME,
//                    DBHelper.NEWS_ORIGINAL_IMAGE_NAME,
//                    DBHelper.NEWS_PARSED_DATE_NAME,
//                    DBHelper.NEWS_AUTHOR_KEY_NAME,
//                    DBHelper.NEWS_AUTHOR_REF_NAME,
//                    DBHelper.NEWS_TYPE_NAME
//            }, selection, selArgs, null,
//                    null, DBHelper.NEWS_POST_TIME_NAME + " " + order, limit);
//
//        } catch (SQLiteException e) {
//            Log.e(TAG, "No table in DB");
//        }
        return cursor;
    }


    public static ContentValues getContentValues(StreamDetails item, long streamId) {
        ContentValues values = new ContentValues();
//        values.put(DBHelper.ID_NAME, item.getKey());
//        values.put(DBHelper.NEWS_STREAM_ID_NAME, streamId);
//        values.put(DBHelper.NEWS_POST_TIME_NAME, item.getTimestamp());
//        values.put(DBHelper.NEWS_NETWORK_NAME, item.getAuthor().getNetwork());
//        values.put(DBHelper.NEWS_AUTHOR_NAME, item.getAuthor().getDisplayName());
//        values.put(DBHelper.NEWS_AUTHOR_KEY_NAME, item.getAuthor().getKey());
//        values.put(DBHelper.NEWS_AUTHOR_REF_NAME, item.getAuthor().getRef());
//        values.put(DBHelper.NEWS_LOGO_NAME, item.getAuthor().getAvatar());
//        values.put(DBHelper.NEWS_TITLE_NAME, item.getCalculatedContentTitle());
//        values.put(DBHelper.NEWS_COMMENT_NAME, item.getCalculatedContentComment());
//        values.put(DBHelper.NEWS_IMAGE_NAME, item.getMainImageUrl());
//        values.put(DBHelper.NEWS_ORIGINAL_IMAGE_NAME, item.getMainOriginalImageUrl());
//        values.put(DBHelper.NEWS_JSON_NAME, gson.toJson(item));
//        values.put(DBHelper.NEWS_TYPE_NAME, item.getType());

        return values;
    }

    public static void closeCursor(Cursor cursor) {
        Log.d(TAG, "closeCursor");
        if (Build.VERSION.SDK_INT < 11 && cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }


    @Override
    public void processFeed(String feed) {
        Gson gson = new Gson();
        BaseResponse streamDetails = gson.fromJson(feed, BaseResponse.class);
        List<StreamDetails> streams = streamDetails.getData().getItems();
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
