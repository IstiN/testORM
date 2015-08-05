package com.epam.testorm.gson;

import java.io.Serializable;

/**
 * Created by Mikhail_Ivanou on 9/11/13.
 */
public class MediaItem implements Serializable {
    private String url;
    private String title;
    private MediaItem image;
    private String original;
    private int mediaType;
    private MediaItem thumbnail;
    private String description;

    public MediaItem() {
    }

    public MediaItem(String url) {
        this.url = url;
    }

    public MediaItem(String url, String title, MediaItem image, String original, MediaItem thumbnail, String description) {
        this.url = url;
        this.title = title;
        this.image = image;
        this.original = original;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public MediaItem(int mediaType) {
        this.mediaType = mediaType;
    }

    public String getUrl() {
        return url;
    }


    public String getTitle() {
        return title;
    }

    public MediaItem getImage() {
        return image;
    }
    public MediaItem getThumbnailUrl() {
        return thumbnail;
    }

    public String getOriginalImage() {
        return original;
    }
    public String getDescription() {
        return description;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }
}
