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
import com.epam.testorm.common.HashUtils;
import com.epam.testorm.db.model.Author;
import com.epam.testorm.db.model.Content;
import com.epam.testorm.db.model.MediaAudios;
import com.epam.testorm.db.model.MediaImages;
import com.epam.testorm.db.model.MediaItem;
import com.epam.testorm.db.model.MediaLinks;
import com.epam.testorm.db.model.MediaVideos;
import com.epam.testorm.db.model.News;
import com.epam.testorm.gson.BaseResponse;
import com.epam.testorm.gson.StreamDetails;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StreamDetailsManager implements ICacheManager {

    private static final String TAG = StreamDetailsManager.class.getSimpleName();

    private DBHelper mDBHelper;

    private Map<Class, String> tablesNames;

    static Class[] tables = new Class[]{
            News.class,
            Author.class,
            Content.class,
            MediaItem.class,
            MediaAudios.class,
            MediaVideos.class,
            MediaImages.class,
            MediaLinks.class,

    };

    public StreamDetailsManager(Context ctx) {
        mDBHelper = DBHelper.getInstance(ctx);
        tablesNames = mDBHelper.createTables(tables);
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
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.beginTransactionNonExclusive();
        clearCache();
        for (int i = 0; i < streams.size(); i++) {
            StreamDetails item = streams.get(i);
            long result = processNews(db, item);
            if (result < 0) {
                db.endTransaction();
                return;
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private long processNews(SQLiteDatabase db, StreamDetails item) {
        ContentValues news = new ContentValues();
        long time = item.getTimestamp() == null ? 0 : item.getTimestamp();
        long newsId = HashUtils.generateId(time, item.getLink());
        news.put(DBHelper.ID_NAME, newsId);
        news.put(News.TIMESTAMP, time);
        StreamDetails.Author itemAuthor = item.getAuthor();  // process author
        if (itemAuthor !=null) {
            ContentValues author = new ContentValues();
            author.put(Author.USER_ID, itemAuthor.getId());
            author.put(Author.AVATAR, itemAuthor.getAvatar());
            author.put(Author.DISPLAY_NAME, itemAuthor.getDisplayName());
            author.put(Author.PROFILE, itemAuthor.getProfileUrl());
            author.put(Author.NETWORK, itemAuthor.getNetwork());
            author.put(Author.REF, itemAuthor.getRef());
            author.put(DBHelper.ID_NAME, itemAuthor.getRef());
            long l = db.insertWithOnConflict(tablesNames.get(Author.class), null, author, SQLiteDatabase.CONFLICT_REPLACE);
            Log.d("DB_RESULT", "author " + l);
            news.put(News.AUTHOR, itemAuthor.getRef());
        }
        com.epam.testorm.gson.MediaItem itemLink = item.getLinkItem();  // process author
        if (itemLink !=null) {
            ContentValues link = new ContentValues();
            link.put(MediaItem.URL, itemLink.getUrl());
            long generateId = HashUtils.generateId(itemLink.getUrl());
            link.put(DBHelper.ID_NAME, generateId);
            long l = db.insertWithOnConflict(tablesNames.get(MediaItem.class), null, link, SQLiteDatabase.CONFLICT_REPLACE);
            Log.d("DB_RESULT", "links " + l);
            news.put(News.URL, generateId);
        }
        ContentValues content = new ContentValues();
        content.put(Content.COMMENT, item.getContentComment());
        content.put(Content.DESCRIPTION, item.getContentDesc());
        content.put(Content.TITLE, item.getContentTitle());
        long generateId = HashUtils.generateId(item.getContentTitle(), item.getContentDesc(), item.getContentComment());
        content.put(DBHelper.ID_NAME, generateId);
        long l = db.insertWithOnConflict(tablesNames.get(Content.class), null, content, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d("DB_RESULT", "content " + l);
        news.put(News.CONTENT, generateId);
        l = db.insertWithOnConflict(tablesNames.get(News.class), null, news, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d("DB_RESULT", "news " + l);
        StreamDetails.Media contentMedia = item.getContentMedia();
        if (contentMedia != null) {
            l = processMedia(db, contentMedia, newsId);
        }

        return l;
    }

    private long processMedia(SQLiteDatabase db, StreamDetails.Media contentMedia, long newsId) {
        long l = 0;
        ArrayList<com.epam.testorm.gson.MediaItem> audios = contentMedia.getAudios();
        if (audios != null) {
            for (com.epam.testorm.gson.MediaItem item : audios) {
                l = addRecord(db, newsId, item, MediaAudios.class);
            }
        }
        ArrayList<com.epam.testorm.gson.MediaItem> videos = contentMedia.getVideos();
        if (videos != null) {
            for (com.epam.testorm.gson.MediaItem item : videos) {
                l = addRecord(db, newsId, item, MediaVideos.class);
            }
        }
        ArrayList<com.epam.testorm.gson.MediaItem> photos = contentMedia.getPhotos();
        if (photos != null) {
            for (com.epam.testorm.gson.MediaItem item : photos) {
                l = addRecord(db, newsId, item, MediaImages.class);
            }
        }
        ArrayList<com.epam.testorm.gson.MediaItem> links = contentMedia.getLinks();
        if (links != null) {
            for (com.epam.testorm.gson.MediaItem item : links) {
                l = addRecord(db, newsId, item, MediaLinks.class);
            }
        }
        return l;
    }

    private long addRecord(SQLiteDatabase db, long newsId, com.epam.testorm.gson.MediaItem item, Class recordClass) {
        ContentValues media = new ContentValues();
        media.put(MediaItem.DESCRIPTION, item.getDescription());
        media.put(MediaItem.URL, item.getUrl());
        media.put(MediaItem.IMAGE, item.getImage() == null ? null : item.getImage().getUrl());
        media.put(MediaItem.ORIGINAL, item.getOriginalImage());
        media.put(MediaItem.THUMBNAIL, item.getThumbnailUrl() == null? null : item.getThumbnailUrl().getUrl());
        media.put(MediaItem.TITLE, item.getTitle());
        long generateId = HashUtils.generateId(item.getUrl());
        media.put(DBHelper.ID_NAME, generateId);
        ContentValues record = new ContentValues();
        record.put(DBHelper.ID_NAME, HashUtils.generateId(newsId, generateId));
        record.put(MediaAudios.newsId, generateId);
        record.put(MediaAudios.itemId, newsId);
        long l = db.insertWithOnConflict(tablesNames.get(MediaItem.class), null, media, SQLiteDatabase.CONFLICT_REPLACE);
        l = db.insertWithOnConflict(tablesNames.get(recordClass), null, record, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d("DB_RESULT", recordClass.getSimpleName() + " " + l);
        return l;
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
