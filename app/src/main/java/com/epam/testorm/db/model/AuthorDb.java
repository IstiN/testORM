package com.epam.testorm.db.model;

import com.epam.testorm.db.ICache;
import com.epam.testorm.db.annotation.dbLong;
import com.epam.testorm.db.annotation.dbString;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class AuthorDb implements ICache {

    @Override
    public String getTableName() {
        return "authors";
    }

    @dbString
    public static String NETWORK = "network";
    @dbString
    public static String USER_ID = "user_id";
    @dbString
    public static String DISPLAY_NAME = "displayName";
    @dbString
    public static String AVATAR = "avatar";
    @dbString
    public static String PROFILE = "profile";
    @dbLong
    public static String REF = "ref";
}
