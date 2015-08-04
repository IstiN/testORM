package com.epam.testorm.db.model;

import com.epam.testorm.db.ICache;
import com.epam.testorm.db.annotation.dbLong;
import com.epam.testorm.db.annotation.dbString;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public abstract class BaseMediaItem implements ICache {

    @dbString
    public static String url;

    @dbString
    public static String title;

    @dbString
    public static String image;

    @dbString
    public static String original;

    @dbString
    public static String thumbnail;

    @dbString
    public static String description;

}
