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
        for (Class table : tables) {
            db.delete(mDBHelper.getTableName(table), null, null);
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
        news.put(News.URL, item.getLink());
        Author itemAuthor = item.getAuthor();  // process author
        if (itemAuthor != null) {
            ContentValues author = new ContentValues();
            author.put(AuthorDb.USER_ID, itemAuthor.getId());
            author.put(AuthorDb.AVATAR, itemAuthor.getAvatar());
            author.put(AuthorDb.DISPLAY_NAME, itemAuthor.getDisplayName());
            author.put(AuthorDb.PROFILE, itemAuthor.getProfileUrl());
            author.put(AuthorDb.NETWORK, itemAuthor.getNetwork());
            author.put(AuthorDb.REF, itemAuthor.getRef());
            author.put(DBHelper.ID_NAME, itemAuthor.getRef());
            long l = db.insertWithOnConflict(tablesNames.get(AuthorDb.class), null, author, SQLiteDatabase.CONFLICT_REPLACE);
            news.put(News.AUTHOR, itemAuthor.getRef());
        }
        com.epam.testorm.gson.MediaItem itemLink = item.getLinkItem();  // process author
        ContentValues content = new ContentValues();
        content.put(ContentDb.COMMENT, item.getContentComment());
        content.put(ContentDb.DESCRIPTION, item.getContentDesc());
        content.put(ContentDb.TITLE, item.getContentTitle());
        long contentId = HashUtils.generateId(item.getContentTitle(), item.getContentDesc(), item.getContentComment());
        content.put(DBHelper.ID_NAME, contentId);
        long l = db.insertWithOnConflict(tablesNames.get(ContentDb.class), null, content, SQLiteDatabase.CONFLICT_REPLACE);
        news.put(News.CONTENT, contentId);
        l = db.insertWithOnConflict(tablesNames.get(News.class), null, news, SQLiteDatabase.CONFLICT_REPLACE);
        Media contentMedia = item.getContentMedia();
        if (contentMedia != null) {
            l = processMedia(db, contentMedia, contentId);
        }

        return l;
    }

    private long processMedia(SQLiteDatabase db, Media contentMedia, long contentId) {
        long l = 0;
        ArrayList<MediaItem> audios = contentMedia.getAudios();
        if (audios != null) {
            for (MediaItem item : audios) {
                l = addRecord(db, contentId, item, MediaAudios.class);
            }
        }
        ArrayList<MediaItem> videos = contentMedia.getVideos();
        if (videos != null) {
            for (MediaItem item : videos) {
                l = addRecord(db, contentId, item, MediaVideos.class);
            }
        }
        ArrayList<MediaItem> photos = contentMedia.getPhotos();
        if (photos != null) {
            for (MediaItem item : photos) {
                l = addRecord(db, contentId, item, MediaImages.class);
            }
        }
        ArrayList<MediaItem> links = contentMedia.getLinks();
        if (links != null) {
            for (MediaItem item : links) {
                l = addRecord(db, contentId, item, MediaLinks.class);
            }
        }
        return l;
    }

    private long addRecord(SQLiteDatabase db, long contentId, MediaItem item, Class<? extends ICache> recordClass) {
        ContentValues media = new ContentValues();
        media.put(MediaItemDb.DESCRIPTION, item.getDescription());
        media.put(MediaItemDb.URL, item.getUrl());
        media.put(MediaItemDb.IMAGE, item.getImage() == null ? null : item.getImage().getUrl());
        media.put(MediaItemDb.ORIGINAL, item.getOriginalImage());
        media.put(MediaItemDb.THUMBNAIL, item.getThumbnailUrl() == null ? null : item.getThumbnailUrl().getUrl());
        media.put(MediaItemDb.TITLE, item.getTitle());
        long generateId = HashUtils.generateId(item.getUrl());
        media.put(DBHelper.ID_NAME, generateId);
        media.put(MediaItemDb.CONTENT_ID, contentId);
        return db.insertWithOnConflict(tablesNames.get(recordClass), null, media, SQLiteDatabase.CONFLICT_REPLACE);
    }

    private Long getLong(Cursor cursor, String column) {
        int columnIndex = cursor.getColumnIndex(column);
        if (columnIndex < 0) {
            return null;
        }
        return cursor.getLong(cursor.getColumnIndex(column));
    }

    private String getString(Cursor cursor, String column) {
        int columnIndex = cursor.getColumnIndex(column);
        if (columnIndex < 0) {
            return null;
        }
        return cursor.getString(columnIndex);
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
        news.close();
        db.setTransactionSuccessful();
        db.endTransaction();
        return createAdapter(list);
    }

    @NonNull
    private StreamDetails getStreamDetails(SQLiteDatabase db, Cursor news) {
        Long id = getLong(news, DBHelper.ID_NAME);
        Long time = getLong(news, News.TIMESTAMP);
        Long authorId = getLong(news, News.AUTHOR);
        Long contentId = getLong(news, News.CONTENT);
        String link = getString(news, News.URL);
        MediaItem url = new MediaItem(link);
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
        authorCursor.close();
        //process content
        Cursor contentCursor = db.query(tablesNames.get(ContentDb.class), null, DBHelper.ID_NAME + " = ?", new String[]{contentId.toString()}, null, null, null);
        contentCursor.moveToFirst();
        String comment = getString(contentCursor, ContentDb.COMMENT);
        String descr = getString(contentCursor, ContentDb.DESCRIPTION);
        String title = getString(contentCursor, ContentDb.TITLE);
        Long contentID = getLong(contentCursor, DBHelper.ID_NAME);
        Media media = getMedia(db, contentID);
        Content content = new Content(media, descr, title, comment);
        contentCursor.close();
        return new StreamDetails(author, content, time, url);
    }

    private Media getMedia(SQLiteDatabase db, Long id) {
        ArrayList<MediaItem> images = new ArrayList<>();
        ArrayList<MediaItem> audios = new ArrayList<>();
        ArrayList<MediaItem> links = new ArrayList<>();
        ArrayList<MediaItem> videos = new ArrayList<>();
        Cursor audiosCursor = db.query(tablesNames.get(MediaAudios.class), null, MediaAudios.CONTENT_ID + " = ?", new String[]{id.toString()}, null, null, null);
        if (audiosCursor.getCount() > 0) {
            while (audiosCursor.moveToNext()) {
                audios.add(getMediaItem(audiosCursor));
            }
        }
        audiosCursor.close();
        Cursor videosCursor = db.query(tablesNames.get(MediaVideos.class), null, MediaVideos.CONTENT_ID + " = ?", new String[]{id.toString()}, null, null, null);
        if (videosCursor.getCount() > 0) {
            while (videosCursor.moveToNext()) {
                videos.add(getMediaItem(videosCursor));
            }
        }
        videosCursor.close();
        Cursor imagesCursor = db.query(tablesNames.get(MediaImages.class), null, MediaImages.CONTENT_ID + " = ?", new String[]{id.toString()}, null, null, null);
        if (imagesCursor.getCount() > 0) {
            while (imagesCursor.moveToNext()) {
                images.add(getMediaItem(imagesCursor));
            }
        }
        imagesCursor.close();
        Cursor linksCursor = db.query(tablesNames.get(MediaLinks.class), null, MediaLinks.CONTENT_ID + " = ?", new String[]{id.toString()}, null, null, null);
        if (linksCursor.getCount() > 0) {
            while (linksCursor.moveToNext()) {
                links.add(getMediaItem(linksCursor));
            }
        }
        linksCursor.close();
        return new Media(images, links, audios, videos);
    }

    @NonNull
    private MediaItem getMediaItem(Cursor cursor) {
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
        news.close();
        db.setTransactionSuccessful();
        db.endTransaction();
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
            ids.add(getLong(query, MediaImages.CONTENT_ID));
        }
        String join = "\"" + TextUtils.join("\",\"", ids) + "\"";
        Cursor news = db.rawQuery("SELECT * FROM News WHERE " + News.CONTENT + " IN (" + join + ") ORDER BY time", null);
        while (news.moveToNext()) {
            StreamDetails detail = getStreamDetails(db, news);
            list.add(detail);
        }
        news.close();
        db.setTransactionSuccessful();
        db.endTransaction();
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
