package com.epam.testorm.db.model;

import com.epam.testorm.db.ICache;
import com.epam.testorm.db.annotation.dbLong;
import com.epam.testorm.db.annotation.dbString;
import com.epam.testorm.gson.MediaItem;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class Author implements ICache {

    @Override
    public String getTableName() {
        return "authors";
    }

    @dbString
    private String network;
    @dbString
    private String id;
    @dbString
    private String displayName;
    @dbString
    private String avatar;
    @dbString
    private String profile;
    @dbLong
    private Long ref;
}
