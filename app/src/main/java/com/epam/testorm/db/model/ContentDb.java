package com.epam.testorm.db.model;

import com.epam.testorm.db.ICache;
import com.epam.testorm.db.annotation.dbString;

/**
 * Created by Mikhail_Ivanou on 8/4/2015.
 */
public class ContentDb implements ICache {
    @Override
    public String getTableName() {
        return "content";
    }

    @dbString
    public static String DESCRIPTION = "description";

    @dbString
    public static String COMMENT = "comment";

    @dbString
    public static String TITLE = "title";

}
