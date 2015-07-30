package com.epam.testorm.sugar.model;

import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class ContentSugar extends SugarRecord {

    @Expose
    private String title;
    @Expose
    private String comment;
    @Expose
    private String description;
    @Expose
    private MediaSugar media;

    public ContentSugar() {
    }

    public ContentSugar(String title, String comment, String description, MediaSugar media) {
        this.title = title;
        this.comment = comment;
        this.description = description;
        this.media = media;
    }


    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public String getDescription() {
        return description;
    }

    public MediaSugar getMedia() {
        return media;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMedia(MediaSugar media) {
        this.media = media;
    }
}
