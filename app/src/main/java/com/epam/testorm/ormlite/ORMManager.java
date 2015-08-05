package com.epam.testorm.ormlite;

import android.app.Activity;
import android.widget.ListAdapter;

import com.epam.testorm.ICacheManager;
import com.epam.testorm.gson.Author;
import com.epam.testorm.gson.BaseResponse;
import com.epam.testorm.gson.StreamDetails;
import com.epam.testorm.ormlite.model.ORMAuthor;
import com.epam.testorm.ormlite.model.ORMNews;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.util.List;

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
        List<StreamDetails> streams = streamDetails.getData().getItems();
        for (StreamDetails item : streams) {
            final ORMAuthor ormAuthor = new ORMAuthor();
            Author author = item.getAuthor();
            ormAuthor.setAvatar(author.getAvatar());
            ormAuthor.setDisplayName(author.getDisplayName());
            ormAuthor.setId(author.getId());
            ormAuthor.setRef(author.getRef());
            ormAuthor.setNetwork(author.getNetwork());
            ormAuthor.setProfile(author.getProfileUrl());
            ORMNews ormNews = new ORMNews();
            ormNews.setAuthor(ormAuthor);
            ormNews.setTime(item.getTimestamp());

            try {
                Dao<ORMNews, Integer> authorDao = getHelper().getNewsDao();
                authorDao.createOrUpdate(ormNews);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
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

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(mActivity, DatabaseHelper.class);
        }
        return databaseHelper;
    }




}
