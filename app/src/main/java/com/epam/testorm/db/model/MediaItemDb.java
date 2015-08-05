package com.epam.testorm.db.model;

import com.epam.testorm.db.ICache;
import com.epam.testorm.db.annotation.dbString;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class MediaItemDb implements ICache {

    @dbString
    public static String URL = "url";

    @dbString
    public static String TITLE = "title";

    @dbString
    public static String IMAGE = "image";

    @dbString
    public static String ORIGINAL = "original";

    @dbString
    public static String THUMBNAIL = "thumbnail";

    @dbString
    public static String DESCRIPTION = "description";

    @Override
    public String getTableName() {
        return "mediaItem";
    }
}
