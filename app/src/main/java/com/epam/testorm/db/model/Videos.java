package com.epam.testorm.db.model;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class Videos extends BaseMediaItem {
    @Override
    public String getTableName() {
        return "videos";
    }
}