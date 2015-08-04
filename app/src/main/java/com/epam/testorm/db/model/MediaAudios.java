package com.epam.testorm.db.model;

import com.epam.testorm.db.ICache;
import com.epam.testorm.db.annotation.dbLong;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class MediaAudios implements ICache {

    @Override
    public String getTableName() {
        return "mediaAudios";
    }

    @dbLong
    public static String newsId = "newsId";
    
    @dbLong
    public static String itemId = "itemId";
}
