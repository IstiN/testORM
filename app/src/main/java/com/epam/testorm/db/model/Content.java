package com.epam.testorm.db.model;

import com.epam.testorm.db.ICache;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class Content implements ICache {
    @Override
    public String getTableName() {
        return "content";
    }

    private String description;
    private String comment;
    private String title;

}
