package com.epam.testorm.db.model;

import com.epam.testorm.db.ICache;
import com.epam.testorm.db.annotation.dbLong;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class MediaImages extends MediaItemDb {

    @Override
    public String getTableName() {
        return "mediaImages";
    }

}
