package com.epam.testorm.realm.model;

import io.realm.RealmObject;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class ContentRealm extends RealmObject {

    private String title;
    private String comment;
    private String description;
    private MediaRealm media;

    public ContentRealm() {
    }

    public ContentRealm(String title, String comment, String description, MediaRealm media) {
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

    public MediaRealm getMedia() {
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

    public void setMedia(MediaRealm media) {
        this.media = media;
    }
}
