package com.epam.testorm.ormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mikhail_Ivanou on 8/6/2015.
 */

public class ORMMediaItem  {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    public String url;

    @DatabaseField
    public String title;

    @DatabaseField
    public String image;

    @DatabaseField
    public String original;

    @DatabaseField
    public String thumbnail;

    @DatabaseField
    public String description;

    @DatabaseField(canBeNull = false, foreign = true)
    private ORMContent content;

    public ORMMediaItem() {
    }

    public ORMMediaItem(Long id, String url, String title, String image, String original, String thumbnail, String description) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.image = image;
        this.original = original;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ORMContent getContent() {
        return content;
    }

    public void setContent(ORMContent content) {
        this.content = content;
    }
}
