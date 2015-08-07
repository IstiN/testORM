package com.epam.testorm.db.model;

import com.epam.testorm.db.ICache;
import com.epam.testorm.db.annotation.dbLong;
import com.epam.testorm.db.annotation.dbString;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class News implements ICache {

    @Override
    public String getTableName() {
        return "News";
    }

    @dbLong
    public static String AUTHOR = "author";

    @dbLong
    public static String CONTENT = "content";

    @dbLong
    public static String TIMESTAMP = "time";

    @dbString
    public static String URL = "url";
}
