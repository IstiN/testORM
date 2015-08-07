package com.epam.testorm.greenDao;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.epam.testorm.ICacheManager;
import com.epam.testorm.R;
import com.epam.testorm.common.AbstractListAdapter;
import com.epam.testorm.common.StringUtils;
import com.epam.testorm.greenDao.model.Audios;
import com.epam.testorm.greenDao.model.AudiosDao;
import com.epam.testorm.greenDao.model.Author;
import com.epam.testorm.greenDao.model.AuthorDao;
import com.epam.testorm.greenDao.model.Content;
import com.epam.testorm.greenDao.model.ContentDao;
import com.epam.testorm.greenDao.model.DaoMaster;
import com.epam.testorm.greenDao.model.DaoSession;
import com.epam.testorm.greenDao.model.Images;
import com.epam.testorm.greenDao.model.ImagesDao;
import com.epam.testorm.greenDao.model.Links;
import com.epam.testorm.greenDao.model.LinksDao;
import com.epam.testorm.greenDao.model.News;
import com.epam.testorm.greenDao.model.NewsDao;
import com.epam.testorm.greenDao.model.Videos;
import com.epam.testorm.greenDao.model.VideosDao;
import com.epam.testorm.gson.BaseResponse;
import com.epam.testorm.gson.Media;
import com.epam.testorm.gson.MediaItem;
import com.epam.testorm.gson.StreamDetails;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Mike on 06.08.2015.
 */
public class GreenDaoManager implements ICacheManager {

    private Activity mActivity;
    private final DaoSession mDaoSession;

    public GreenDaoManager(Activity activity) {
        mActivity = activity;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(activity, "green-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    @Override
    public void processFeed(String string) {
        Gson gson = new Gson();
        BaseResponse streamDetails = gson.fromJson(string, BaseResponse.class);
        final List<StreamDetails> streams = streamDetails.getData().getItems();
        mDaoSession.runInTx(new Runnable() {
            @Override
            public void run() {
                clearCache();
                saveStreams(streams);
            }
        });
    }

    private void clearCache() {
        AuthorDao authorDao = mDaoSession.getAuthorDao();
        ContentDao contentDao = mDaoSession.getContentDao();
        NewsDao newsDao = mDaoSession.getNewsDao();
        AudiosDao audiosDao = mDaoSession.getAudiosDao();
        ImagesDao imagesDao = mDaoSession.getImagesDao();
        LinksDao linksDao = mDaoSession.getLinksDao();
        VideosDao videosDao = mDaoSession.getVideosDao();
        authorDao.deleteAll();
        contentDao.deleteAll();
        newsDao.deleteAll();
        audiosDao.deleteAll();
        imagesDao.deleteAll();
        linksDao.deleteAll();
        videosDao.deleteAll();
    }

    private void saveStreams(List<StreamDetails> streams) {
        AuthorDao authorDao = mDaoSession.getAuthorDao();
        ContentDao contentDao = mDaoSession.getContentDao();
        NewsDao newsDao = mDaoSession.getNewsDao();
        AudiosDao audiosDao = mDaoSession.getAudiosDao();
        ImagesDao imagesDao = mDaoSession.getImagesDao();
        LinksDao linksDao = mDaoSession.getLinksDao();
        VideosDao videosDao = mDaoSession.getVideosDao();
        for (StreamDetails item : streams) {

            Author author = new Author();
            com.epam.testorm.gson.Author authorInitial = item.getAuthor();
            author.setId(authorInitial.getRef());
            author.setDisplayName(authorInitial.getDisplayName());
            author.setNetwork(authorInitial.getNetwork());
            author.setRef(authorInitial.getRef());
            author.setUser_id(authorInitial.getId());
            author.setAvatar(authorInitial.getAvatar());
            author.setProfile(authorInitial.getProfileUrl());
            authorDao.insertOrReplace(author);

            Content content = new Content();
            content.setComment(item.getContentComment());
            content.setDescription(item.getContentDesc());
            content.setTitle(item.getContentTitle());
            contentDao.insertOrReplace(content);

            Media contentMedia = item.getContentMedia();
            if (contentMedia != null) {
                ArrayList<MediaItem> photos = contentMedia.getPhotos();
                if (photos != null) {
                    for (MediaItem mediaItem : photos) {
                        Images media = new Images();
                        media.setUrl(mediaItem.getUrl());
                        media.setTitle(mediaItem.getTitle());
                        media.setDescription(mediaItem.getDescription());
                        media.setImage(mediaItem.getImage() == null ? null : mediaItem.getImage().getUrl());
                        media.setThumbnail(mediaItem.getThumbnailUrl() == null ? null : mediaItem.getThumbnailUrl().getUrl());
                        media.setContent(content);
                        imagesDao.insertOrReplace(media);
                    }
                }
                ArrayList<MediaItem> videos = contentMedia.getVideos();
                if (videos != null) {
                    for (MediaItem mediaItem : videos) {
                        Videos media = new Videos();
                        media.setUrl(mediaItem.getUrl());
                        media.setTitle(mediaItem.getTitle());
                        media.setDescription(mediaItem.getDescription());
                        media.setImage(mediaItem.getImage() == null ? null : mediaItem.getImage().getUrl());
                        media.setThumbnail(mediaItem.getThumbnailUrl() == null ? null : mediaItem.getThumbnailUrl().getUrl());
                        media.setContent(content);
                        videosDao.insertOrReplace(media);
                    }
                }
                ArrayList<MediaItem> audios = contentMedia.getAudios();
                if (audios != null) {
                    for (MediaItem mediaItem : audios) {
                        Audios media = new Audios();
                        media.setUrl(mediaItem.getUrl());
                        media.setTitle(mediaItem.getTitle());
                        media.setDescription(mediaItem.getDescription());
                        media.setImage(mediaItem.getImage() == null ? null : mediaItem.getImage().getUrl());
                        media.setThumbnail(mediaItem.getThumbnailUrl() == null ? null : mediaItem.getThumbnailUrl().getUrl());
                        media.setContent(content);
                        audiosDao.insertOrReplace(media);
                    }
                }
                ArrayList<MediaItem> links = contentMedia.getLinks();
                if (links != null) {
                    for (MediaItem mediaItem : links) {
                        Links media = new Links();
                        media.setUrl(mediaItem.getUrl());
                        media.setTitle(mediaItem.getTitle());
                        media.setDescription(mediaItem.getDescription());
                        media.setImage(mediaItem.getImage() == null ? null : mediaItem.getImage().getUrl());
                        media.setThumbnail(mediaItem.getThumbnailUrl() == null ? null : mediaItem.getThumbnailUrl().getUrl());
                        media.setContent(content);
                        linksDao.insertOrReplace(media);
                    }
                }
            }

            News news = new News();
            news.setUrl(item.getLink());
            news.setTimestamp(item.getTimestamp());
            news.setAuthor(author);
            news.setContent(content);
            newsDao.insertOrReplace(news);

        }
    }

    @Override
    public ListAdapter getFullAdapter() {
        List<News> newses = mDaoSession.getNewsDao().loadAll();
        return createAdapter(newses);
    }

    @Override
    public ListAdapter getFilteredAdapter() {
        QueryBuilder<Author> authorQueryBuilder = mDaoSession.getAuthorDao().queryBuilder();
        authorQueryBuilder.whereOr(AuthorDao.Properties.DisplayName.eq("AdMe.ru"), AuthorDao.Properties.DisplayName.eq("Lenta.Ru"));
        List<Author> list = authorQueryBuilder.build().list();
        List<String> ids = new ArrayList<>();
        for (Author item : list) {
            ids.add(String.valueOf(item.getId()));
        }
        QueryBuilder<News> newsQueryBuilder = mDaoSession.getNewsDao().queryBuilder();
        newsQueryBuilder.where(NewsDao.Properties.AuthorId.in(ids));
        return createAdapter(newsQueryBuilder.list());
    }

    @Override
    public ListAdapter getImagesOnlyAdapter() {
        List<Images> images = mDaoSession.getImagesDao().loadAll();
        Set<String> ids = new HashSet<>();
        for (Images item : images) {
            ids.add(String.valueOf(item.getContentId()));
        }
        QueryBuilder<News> newsQueryBuilder = mDaoSession.getNewsDao().queryBuilder();
        newsQueryBuilder.where(NewsDao.Properties.ContentId.in(ids));
        return createAdapter(newsQueryBuilder.list());
    }

    @NonNull
    private ListAdapter createAdapter(final List<News> all) {
        BaseAdapter adapter = new AbstractListAdapter<News>(mActivity, R.layout.item, all) {

            @Override
            protected void initView(View convertView, News item, int position) {
                updateView(convertView, item);
            }
        };
        return adapter;
    }

    private void updateView(View convertView, News channel) {

        ImageView posterView = (ImageView) convertView.findViewById(R.id.image);
        String posterUrl = null;
        String posterTitle = null;
        int mediaCount = 0;

        List<Images> photos = channel.getContent().getImages();
        List<Links> urls = channel.getContent().getLinks();
        List<Videos> videos = channel.getContent().getVideos();

        if (photos != null && photos.size() > 0) {
            posterUrl = photos.get(0).getUrl();
            posterTitle = photos.get(0).getTitle();
            mediaCount = photos.size();
        } else if (urls != null && urls.size() > 0) {
            posterUrl = urls.get(0).getImage();
            posterTitle = urls.get(0).getUrl();
        } else if (videos != null && videos.size() > 0) {
            posterUrl = videos.get(0).getThumbnail();
            posterTitle = videos.get(0).getDescription();
        }
        if (urls != null && urls.size() > 0) {
            mediaCount += urls.size();
        }
        if (videos != null && videos.size() > 0) {
            mediaCount += videos.size();
        }

        ImageLoader.getInstance().displayImage(posterUrl, posterView);
        ImageView avatarView = (ImageView) convertView.findViewById(R.id.avatar);
        ImageLoader.getInstance().displayImage(channel.getAuthor().getAvatar(), avatarView);
        setText(convertView, R.id.title, channel.getAuthor().getDisplayName());
        setText(convertView, R.id.desc, StringUtils.isEmpty(channel.getContent().getTitle()) ? channel.getContent().getDescription() : channel.getContent().getTitle());

        setText(convertView, R.id.videoUrl, posterTitle);
        setText(convertView, R.id.videoKey, String.valueOf(mediaCount));
        setText(convertView, R.id.entitlements, channel.getContent().getComment());

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
