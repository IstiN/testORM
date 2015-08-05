package com.epam.testorm.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.epam.testorm.ICacheManager;
import com.epam.testorm.R;
import com.epam.testorm.common.AbstractListAdapter;
import com.epam.testorm.common.HashUtils;
import com.epam.testorm.common.StringUtils;
import com.epam.testorm.db.model.AuthorDb;
import com.epam.testorm.db.model.ContentDb;
import com.epam.testorm.db.model.MediaAudios;
import com.epam.testorm.db.model.MediaImages;
import com.epam.testorm.db.model.MediaItemDb;
import com.epam.testorm.db.model.MediaLinks;
import com.epam.testorm.db.model.MediaVideos;
import com.epam.testorm.db.model.News;
import com.epam.testorm.gson.Author;
import com.epam.testorm.gson.BaseResponse;
import com.epam.testorm.gson.Content;
import com.epam.testorm.gson.Media;
import com.epam.testorm.gson.MediaItem;
import com.epam.testorm.gson.StreamDetails;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StreamDetailsManager implements ICacheManager {

    private static final String TAG = StreamDetailsManager.class.getSimpleName();

    private DBHelper mDBHelper;

    private Map<Class, String> tablesNames;

    private Activity mActivity;

    static Class[] tables = new Class[]{
            News.class,
            AuthorDb.class,
            ContentDb.class,
            MediaItemDb.class,
            MediaAudios.class,
            MediaVideos.class,
            MediaImages.class,
            MediaLinks.class,

    };

    public StreamDetailsManager(Activity activity) {
        mActivity = activity;
        mDBHelper = DBHelper.getInstance(activity);
        tablesNames = mDBHelper.createTables(tables);
    }

    public void clearCache() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        for (Class table : tables ) {
            db.delete(mDBHelper.getTableName(table), null, null);
        }
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
        Author itemAuthor = item.getAuthor();  // process author
        if (itemAuthor !=null) {
            ContentValues author = new ContentValues();
            author.put(AuthorDb.USER_ID, itemAuthor.getId());
            author.put(AuthorDb.AVATAR, itemAuthor.getAvatar());
            author.put(AuthorDb.DISPLAY_NAME, itemAuthor.getDisplayName());
            author.put(AuthorDb.PROFILE, itemAuthor.getProfileUrl());
            author.put(AuthorDb.NETWORK, itemAuthor.getNetwork());
            author.put(AuthorDb.REF, itemAuthor.getRef());
            author.put(DBHelper.ID_NAME, itemAuthor.getRef());
            long l = db.insertWithOnConflict(tablesNames.get(AuthorDb.class), null, author, SQLiteDatabase.CONFLICT_REPLACE);
            Log.d("DB_RESULT", "author " + l);
            news.put(News.AUTHOR, itemAuthor.getRef());
        }
        com.epam.testorm.gson.MediaItem itemLink = item.getLinkItem();  // process author
        if (itemLink !=null) {
            ContentValues link = new ContentValues();
            link.put(MediaItemDb.URL, itemLink.getUrl());
            long generateId = HashUtils.generateId(itemLink.getUrl());
            link.put(DBHelper.ID_NAME, generateId);
            long l = db.insertWithOnConflict(tablesNames.get(MediaItemDb.class), null, link, SQLiteDatabase.CONFLICT_REPLACE);
            news.put(News.URL, generateId);
        }
        ContentValues content = new ContentValues();
        content.put(ContentDb.COMMENT, item.getContentComment());
        content.put(ContentDb.DESCRIPTION, item.getContentDesc());
        content.put(ContentDb.TITLE, item.getContentTitle());
        long generateId = HashUtils.generateId(item.getContentTitle(), item.getContentDesc(), item.getContentComment());
        content.put(DBHelper.ID_NAME, generateId);
        long l = db.insertWithOnConflict(tablesNames.get(ContentDb.class), null, content, SQLiteDatabase.CONFLICT_REPLACE);
        news.put(News.CONTENT, generateId);
        l = db.insertWithOnConflict(tablesNames.get(News.class), null, news, SQLiteDatabase.CONFLICT_REPLACE);
        Media contentMedia = item.getContentMedia();
        if (contentMedia != null) {
            l = processMedia(db, contentMedia, newsId);
        }

        return l;
    }

    private long processMedia(SQLiteDatabase db, Media contentMedia, long newsId) {
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
        media.put(MediaItemDb.DESCRIPTION, item.getDescription());
        media.put(MediaItemDb.URL, item.getUrl());
        media.put(MediaItemDb.IMAGE, item.getImage() == null ? null : item.getImage().getUrl());
        media.put(MediaItemDb.ORIGINAL, item.getOriginalImage());
        media.put(MediaItemDb.THUMBNAIL, item.getThumbnailUrl() == null? null : item.getThumbnailUrl().getUrl());
        media.put(MediaItemDb.TITLE, item.getTitle());
        long generateId = HashUtils.generateId(item.getUrl());
        media.put(DBHelper.ID_NAME, generateId);
        ContentValues record = new ContentValues();
        record.put(DBHelper.ID_NAME, HashUtils.generateId(newsId, generateId));
        record.put(MediaAudios.newsId, newsId);
        record.put(MediaAudios.itemId, generateId);
        long l = db.insertWithOnConflict(tablesNames.get(MediaItemDb.class), null, media, SQLiteDatabase.CONFLICT_REPLACE);
        l = db.insertWithOnConflict(tablesNames.get(recordClass), null, record, SQLiteDatabase.CONFLICT_REPLACE);
        return l;
    }

    private Long getLong(Cursor cursor, String column) {
        return cursor.getLong(cursor.getColumnIndex(column));
    }
    private String getString(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    @Override
    public ListAdapter getFullAdapter() {
        ArrayList<StreamDetails> list = new ArrayList<>();
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.beginTransactionNonExclusive();
        Cursor news = db.query(tablesNames.get(News.class), null, null, null, null, null, News.TIMESTAMP);
        while (news.moveToNext()) {
            StreamDetails detail = getStreamDetails(db, news);
            list.add(detail);
        }
        closeCursor(news);
        return createAdapter(list);
    }

    @NonNull
    private StreamDetails getStreamDetails(SQLiteDatabase db, Cursor news) {
        Long id = getLong(news, DBHelper.ID_NAME);
        Long time = getLong(news, News.TIMESTAMP);
        Long authorId = getLong(news, News.AUTHOR);
        Long contentId = getLong(news, News.CONTENT);
        Long urlId = getLong(news, News.URL);
        Cursor urlCursor = db.query(tablesNames.get(MediaItemDb.class), null, DBHelper.ID_NAME + " = ?", new String[]{urlId.toString()}, null, null, null);
        urlCursor.moveToFirst();
        MediaItem url = new MediaItem(getString(urlCursor, MediaItemDb.URL));
        closeCursor(urlCursor);
        //process author
        Cursor authorCursor = db.query(tablesNames.get(AuthorDb.class), null, DBHelper.ID_NAME + " = ?", new String[]{authorId.toString()}, null, null, null);
        authorCursor.moveToFirst();
        String avatar = getString(authorCursor, AuthorDb.AVATAR);
        String displayName = getString(authorCursor, AuthorDb.DISPLAY_NAME);
        String network = getString(authorCursor, AuthorDb.NETWORK);
        String profile = getString(authorCursor, AuthorDb.PROFILE);
        Long ref = getLong(authorCursor, AuthorDb.REF);
        String userId = getString(authorCursor, AuthorDb.USER_ID);
        MediaItem authorAvatar = new MediaItem(avatar);
        MediaItem authorProfile = new MediaItem(profile);
        Author author = new Author(network, userId, displayName, authorAvatar, authorProfile, ref);
        closeCursor(authorCursor);
        //process content
        Cursor contentCursor = db.query(tablesNames.get(ContentDb.class), null, DBHelper.ID_NAME + " = ?", new String[]{contentId.toString()}, null, null, null);
        contentCursor.moveToFirst();
        String comment = getString(contentCursor, ContentDb.COMMENT);
        String descr = getString(contentCursor, ContentDb.DESCRIPTION);
        String title = getString(contentCursor, ContentDb.TITLE);
        Media media = getMedia(db,id);
        Content content = new Content(media, descr, title, comment);
        closeCursor(contentCursor);
        return new StreamDetails(author, content, time, url);
    }

    private Media getMedia(SQLiteDatabase db, Long id) {
        ArrayList<MediaItem> images = new ArrayList<>();
        ArrayList<MediaItem> audios = new ArrayList<>();
        ArrayList<MediaItem> links = new ArrayList<>();
        ArrayList<MediaItem> videos = new ArrayList<>();
        Cursor audiosCursor = db.query(tablesNames.get(MediaAudios.class), null, MediaAudios.newsId + " = ?", new String[]{id.toString()}, null, null, null);
        while (audiosCursor.moveToNext()) {
            audios.add(getMediaItem(db, getLong(audiosCursor, MediaAudios.itemId)));
        }
        closeCursor(audiosCursor);
        Cursor videosCursor = db.query(tablesNames.get(MediaVideos.class), null, MediaVideos.newsId + " = ?", new String[]{id.toString()}, null, null, null);
        while (videosCursor.moveToNext()) {
            videos.add(getMediaItem(db, getLong(videosCursor, MediaVideos.itemId)));
        }
        closeCursor(videosCursor);
        Cursor imagesCursor = db.query(tablesNames.get(MediaImages.class), null, MediaImages.newsId + " = ?", new String[]{id.toString()}, null, null, null);
        while (imagesCursor.moveToNext()) {
            images.add(getMediaItem(db, getLong(imagesCursor, MediaImages.itemId)));
        }
        closeCursor(imagesCursor);
        Cursor linksCursor = db.query(tablesNames.get(MediaLinks.class), null, MediaLinks.newsId + " = ?", new String[]{id.toString()}, null, null, null);
        while (linksCursor.moveToNext()) {
            links.add(getMediaItem(db, getLong(linksCursor, MediaLinks.itemId)));
        }
        closeCursor(linksCursor);
        return new Media(images, links, audios, videos);
    }

    @NonNull
    private MediaItem getMediaItem(SQLiteDatabase db, Long id) {
        Cursor cursor = db.query(tablesNames.get(MediaItemDb.class), null, DBHelper.ID_NAME + " = ?", new String[]{id.toString()}, null, null, null);
        cursor.moveToFirst();
        String url = getString(cursor, MediaItemDb.URL);
        String desc = getString(cursor, MediaItemDb.DESCRIPTION);
        String image = getString(cursor, MediaItemDb.IMAGE);
        String original = getString(cursor, MediaItemDb.ORIGINAL);
        String thumbnail = getString(cursor, MediaItemDb.THUMBNAIL);
        String title = getString(cursor, MediaItemDb.TITLE);
        MediaItem imageItem = null;
        if (!TextUtils.isEmpty(image)) {
            imageItem = new MediaItem(image);
        }
        MediaItem thumbItem = null;
        if (!TextUtils.isEmpty(thumbnail)) {
            thumbItem = new MediaItem(thumbnail);
        }
        closeCursor(cursor);
        return new MediaItem(url, title, imageItem, original, thumbItem, desc);
    }

    @Override
    public ListAdapter getFilteredAdapter() {
        ArrayList<StreamDetails> list = new ArrayList<>();
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.beginTransactionNonExclusive();
        Cursor query = db.query(tablesNames.get(AuthorDb.class), null, AuthorDb.DISPLAY_NAME + " = ? OR " + AuthorDb.DISPLAY_NAME + " = ? ", new String[]{"Lenta.Ru", "AdMe.Ru"}, null, null, null);
        String[] selectionArgs = new String[query.getCount()];
        int i = 0;
        while (query.moveToNext()) {
            selectionArgs[i] = getLong(query, AuthorDb.REF).toString();
        }

        Cursor news = db.query(tablesNames.get(News.class), null, News.AUTHOR + " = ? OR " + News.AUTHOR + " = ? ", selectionArgs, null, null, News.TIMESTAMP);
        while (news.moveToNext()) {
            StreamDetails detail = getStreamDetails(db, news);
            list.add(detail);
        }
        closeCursor(news);
        return createAdapter(list);

    }

    @Override
    public ListAdapter getImagesOnlyAdapter() {
        ArrayList<StreamDetails> list = new ArrayList<>();
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.beginTransactionNonExclusive();
        Cursor query = db.query(tablesNames.get(MediaImages.class), null, null, null, null, null, null);
        Set<Long> ids = new HashSet<>();
        while (query.moveToNext()) {
            ids.add(getLong(query, MediaImages.newsId));
        }
        String join = "\"" + TextUtils.join("\",\"", ids) + "\"";
        Cursor news = db.rawQuery("SELECT * FROM News WHERE _ID IN ("+join+") ORDER BY time", null);
        while (news.moveToNext()) {
            StreamDetails detail = getStreamDetails(db, news);
            list.add(detail);
        }
        closeCursor(news);
        return createAdapter(list);
    }

    @NonNull
    private ListAdapter createAdapter(final ArrayList<StreamDetails> all) {
        BaseAdapter adapter = new AbstractListAdapter<StreamDetails>(mActivity, R.layout.item, all) {

            @Override
            protected void initView(View convertView, StreamDetails item, int position) {
                updateView(convertView, item);
            }
        };
        return adapter;
    }

    private void updateView(View convertView, StreamDetails channel) {
        ImageView posterView = (ImageView) convertView.findViewById(R.id.image);
        Media media = channel.getContentMedia();
        String posterUrl = null;
        String posterTitle = null;
        int mediaCount = 0;
        if (media != null) {
            List<com.epam.testorm.gson.MediaItem> photos = media.getPhotos();
            List<com.epam.testorm.gson.MediaItem> urls = media.getLinks();
            List<com.epam.testorm.gson.MediaItem> videos = media.getVideos();

            if (photos != null && photos.size() > 0) {
                posterUrl = photos.get(0).getUrl();
                posterTitle = photos.get(0).getTitle();
                mediaCount = photos.size();
            } else if (urls != null && urls.size() > 0 && urls.get(0).getImage() != null) {
                posterUrl = urls.get(0).getImage().getUrl();
                posterTitle = urls.get(0).getUrl();
            } else if (videos != null && videos.size() > 0 && videos.get(0).getThumbnailUrl() != null) {
                posterUrl = videos.get(0).getThumbnailUrl().getUrl();
                posterTitle = videos.get(0).getDescription();
            }
            if (urls != null && urls.size() > 0) {
                mediaCount += urls.size();
            }
            if (videos != null && videos.size() > 0) {
                mediaCount += videos.size();
            }
        }
        ImageLoader.getInstance().displayImage(posterUrl, posterView);
        ImageView avatarView = (ImageView) convertView.findViewById(R.id.avatar);
        ImageLoader.getInstance().displayImage(channel.getAuthor().getAvatar(), avatarView);
        setText(convertView, R.id.title, channel.getAuthor().getDisplayName());
        setText(convertView, R.id.desc, StringUtils.isEmpty(channel.getContentTitle()) ? channel.getContentDesc() : channel.getContentTitle());

        setText(convertView, R.id.videoUrl, posterTitle);
        setText(convertView, R.id.videoKey, String.valueOf(mediaCount));
        setText(convertView, R.id.entitlements, channel.getContentComment());


    }

    private void setText(View view, int id, String value) {
        TextView number = (TextView) view.findViewById(id);
        if (!TextUtils.isEmpty(value)) {
            number.setText(value);
            number.setVisibility(View.VISIBLE);
        } else {
            number.setVisibility(View.GONE);
        }
    }
}
