package com.epam.testorm.realm.model;

import io.realm.RealmObject;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class MediaItemRealm extends RealmObject {

    private String url;
    private String title;
    private String description;
    private MediaItemRealm image;
    private MediaItemRealm thumbnail;

    public MediaItemRealm() {
    }

    public MediaItemRealm(String url, String title, MediaItemRealm image) {
        this.url = url;
        this.title = title;
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MediaItemRealm getImage() {
        return image;
    }

    public void setImage(MediaItemRealm image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public MediaItemRealm getThumbnail() {
        return thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(MediaItemRealm thumbnail) {
        this.thumbnail = thumbnail;
    }
}
