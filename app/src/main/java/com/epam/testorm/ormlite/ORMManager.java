package com.epam.testorm.ormlite;

import android.app.Activity;
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
import com.epam.testorm.gson.Author;
import com.epam.testorm.gson.BaseResponse;
import com.epam.testorm.gson.Media;
import com.epam.testorm.gson.MediaItem;
import com.epam.testorm.gson.StreamDetails;
import com.epam.testorm.ormlite.model.ORMAudios;
import com.epam.testorm.ormlite.model.ORMAuthor;
import com.epam.testorm.ormlite.model.ORMContent;
import com.epam.testorm.ormlite.model.ORMImages;
import com.epam.testorm.ormlite.model.ORMLinks;
import com.epam.testorm.ormlite.model.ORMMediaItem;
import com.epam.testorm.ormlite.model.ORMNews;
import com.epam.testorm.ormlite.model.ORMVideos;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Mike on 05.08.2015.
 */
public class ORMManager implements ICacheManager {

    private Activity mActivity;
    private DatabaseHelper databaseHelper = null;

    public ORMManager(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void processFeed(String string) {
        Gson gson = new Gson();
        BaseResponse streamDetails = gson.fromJson(string, BaseResponse.class);

        final List<StreamDetails> streams = streamDetails.getData().getItems();
        try {
            final Dao<ORMAuthor, Integer> authorDao = getHelper().getAuthorDao();
            final Dao<ORMNews, Integer> newsDao = getHelper().getNewsDao();
            final Dao<ORMContent, Integer> contentDao = getHelper().getContentDao();
            final Dao<ORMImages, Integer> imagesDao = getHelper().getImagesDao();
            final Dao<ORMAudios, Integer> audiosDao = getHelper().getAudiosDao();
            final Dao<ORMLinks, Integer> linksDao = getHelper().getLinksDao();
            final Dao<ORMVideos, Integer> videosDao = getHelper().getVideosDao();
            newsDao.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    newsDao.clearObjectCache();
                    authorDao.clearObjectCache();
                    contentDao.clearObjectCache();
                    imagesDao.clearObjectCache();
                    audiosDao.clearObjectCache();
                    linksDao.clearObjectCache();
                    videosDao.clearObjectCache();
                    for (StreamDetails item : streams) {
                        final ORMAuthor ormAuthor = new ORMAuthor();
                        Author author = item.getAuthor();
                        ormAuthor.setAvatar(author.getAvatar());
                        ormAuthor.setDisplayName(author.getDisplayName());
                        ormAuthor.setId(author.getId());
                        ormAuthor.setRef(author.getRef());
                        ormAuthor.setNetwork(author.getNetwork());
                        ormAuthor.setProfile(author.getProfileUrl());
                        Dao.CreateOrUpdateStatus orUpdate = authorDao.createOrUpdate(ormAuthor);

                        ORMContent ormContent = new ORMContent();
                        ormContent.setComment(item.getContentComment());
                        ormContent.setDescription(item.getContentDesc());
                        ormContent.setTitle(item.getContentTitle());
                        orUpdate = contentDao.createOrUpdate(ormContent);
                        Media contentMedia = item.getContentMedia();
                        if (contentMedia != null) {
                            ormContent.setImages(processMedia(ormContent, contentMedia.getPhotos(), imagesDao));
                            ormContent.setVideos(processMedia(ormContent, contentMedia.getVideos(), videosDao));
                            ormContent.setLinks(processMedia(ormContent, contentMedia.getLinks(), linksDao));
                            ormContent.setAudios(processMedia(ormContent, contentMedia.getAudios(), audiosDao));
                        }
                        orUpdate = contentDao.createOrUpdate(ormContent);
                        ORMNews ormNews = new ORMNews();
                        ormNews.setAuthor(ormAuthor);
                        ormNews.setTime(item.getTimestamp());
                        ormNews.setUrl(item.getLink());
                        ormNews.setContent(ormContent);
                        newsDao.createOrUpdate(ormNews);


                    }
                    return null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Collection processMedia(ORMContent ormContent, ArrayList<MediaItem> photos, Dao imagesDao) throws java.sql.SQLException {
        if (photos != null) {
            Collection images = new ArrayList<>();
            for (MediaItem photo : photos) {
                ORMMediaItem ormPhoto = new ORMMediaItem();
                ormPhoto.setTitle(photo.getTitle());
                ormPhoto.setUrl(photo.getUrl());
                ormPhoto.setDescription(photo.getDescription());
                ormPhoto.setThumbnail(photo.getThumbnailUrl() == null ? null : photo.getThumbnailUrl().getUrl());
                ormPhoto.setImage(photo.getImage() == null ? null : photo.getImage().getUrl());
                ormPhoto.setContent(ormContent);
                imagesDao.createOrUpdate(ormPhoto);
                images.add(ormPhoto);
            }
            return images;
        }
        return null;
    }

    @Override
    public ListAdapter getFullAdapter() {
        try {
            Dao<ORMNews, Integer> newsDao = getHelper().getNewsDao();
            List<ORMNews> ormNewses = newsDao.queryForAll();
            return createAdapter(new ArrayList<ORMNews>(ormNewses));
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ListAdapter getFilteredAdapter() {
        try {
            Dao<ORMNews, Integer> newsDao = getHelper().getNewsDao();
            Dao<ORMAuthor, Integer> authorDao = getHelper().getAuthorDao();
            QueryBuilder<ORMAuthor,Integer> uQb = authorDao.queryBuilder();
            uQb.where().eq(ORMAuthor.DISPLAY_NAME, "Lenta.Ru").or().eq(ORMAuthor.DISPLAY_NAME, "AdMe.Ru");
            final QueryBuilder<ORMNews, Integer> queryBuilder = newsDao.queryBuilder();
            queryBuilder.where().in(ORMNews.AUTHOR, uQb.query());
            List<ORMNews> results = queryBuilder.query();
            return createAdapter(new ArrayList<ORMNews>(results));
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ListAdapter getImagesOnlyAdapter() {
        try {
            Dao<ORMNews, Integer> newsDao = getHelper().getNewsDao();
            Dao<ORMImages, Integer> imagesDao = getHelper().getImagesDao();
            Dao<ORMContent, Integer> contentDao = getHelper().getContentDao();
            QueryBuilder<ORMContent, Integer> ormContentQueryBuilder = contentDao.queryBuilder();
            List<ORMImages> objects = imagesDao.queryForAll();
            List<Long> ids = new ArrayList<>();
            for (ORMImages image : objects) {
                ids.add(image.getContent().getId());
            }
            String queryParam = "\"" + TextUtils.join("\",\"", ids) + "\"";
            List<ORMContent> ormContents = ormContentQueryBuilder.where().in("id", queryParam).query();
            final QueryBuilder<ORMNews, Integer> queryBuilder = newsDao.queryBuilder();
            queryBuilder.where().in(ORMNews.CONTENT, ormContents);
            List<ORMNews> results = queryBuilder.query();
            return createAdapter(new ArrayList<ORMNews>(results));
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(mActivity, DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @NonNull
    private ListAdapter createAdapter(final ArrayList<ORMNews> all) {
        BaseAdapter adapter = new AbstractListAdapter<ORMNews>(mActivity, R.layout.item, all) {

            @Override
            protected void initView(View convertView, ORMNews item, int position) {
                updateView(convertView, item);
            }
        };
        return adapter;
    }

    private void updateView(View convertView, ORMNews channel) {

        ImageView posterView = (ImageView) convertView.findViewById(R.id.image);
        String posterUrl = null;
        String posterTitle = null;
        int mediaCount = 0;

        ArrayList<ORMMediaItem> photos = new ArrayList<ORMMediaItem>(channel.getContent().getImages());
        ArrayList<ORMMediaItem> urls = new ArrayList<ORMMediaItem>(channel.getContent().getLinks());
        ArrayList<ORMMediaItem> videos = new ArrayList<ORMMediaItem>(channel.getContent().getVideos());

        if (photos.size() > 0) {
            posterUrl = photos.get(0).getUrl();
            posterTitle = photos.get(0).getTitle();
            mediaCount = photos.size();
        } else if (urls.size() > 0) {
            posterUrl = urls.get(0).getImage();
            posterTitle = urls.get(0).getUrl();
        } else if (videos.size() > 0) {
            posterUrl = videos.get(0).getThumbnail();
            posterTitle = videos.get(0).getDescription();
        }
        if (urls.size() > 0) {
            mediaCount += urls.size();
        }
        if (videos.size() > 0) {
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
