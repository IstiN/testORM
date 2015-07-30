package com.epam.testorm.sugar.model;

import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class MediaItemSugar extends SugarRecord {

    @Expose
    private String url;
    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private MediaItemSugar image;
    @Expose
    private MediaItemSugar thumbnail;

    public MediaItemSugar() {
    }

    public MediaItemSugar(String url, String title, MediaItemSugar image) {
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

    public MediaItemSugar getImage() {
        return image;
    }

    public void setImage(MediaItemSugar image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public MediaItemSugar getThumbnail() {
        return thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(MediaItemSugar thumbnail) {
        this.thumbnail = thumbnail;
    }
}
