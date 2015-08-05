package com.epam.testorm.ormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mike on 05.08.2015.
 */

@DatabaseTable(tableName = "ORMNews")
public class ORMNews {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private Long time;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private ORMAuthor author;

    public ORMNews() {
    }

    public ORMNews(Long id, Long time, ORMAuthor author) {
        this.id = id;
        this.time = time;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public ORMAuthor getAuthor() {
        return author;
    }

    public void setAuthor(ORMAuthor author) {
        this.author = author;
    }
}
