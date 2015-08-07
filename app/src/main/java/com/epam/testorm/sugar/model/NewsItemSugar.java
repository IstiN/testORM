package com.epam.testorm.sugar.model;

import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class NewsItemSugar extends SugarRecord {

    @Expose
    @Table
    private ContentSugar content;
    @Expose
    private long timestamp;
    @Expose
    @Table
    private AuthorSugar author;
    @Expose
    @Table
    private String link;

    public NewsItemSugar() {
    }

    public NewsItemSugar(AuthorSugar author, ContentSugar content, long timestamp, String link) {
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
        this.link = link;
    }


    public AuthorSugar getAuthor() {
        return author;
    }

    public void setAuthor(AuthorSugar author) {
        this.author = author;
    }

    public ContentSugar getContent() {
        return content;
    }

    public void setContent(ContentSugar content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
