package com.epam.testorm.ormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by Mikhail_Ivanou on 8/6/2015.
 */

@DatabaseTable(tableName = "ORMContent")
public class ORMContent {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private  String description;

    @DatabaseField
    private  String comment;

    @DatabaseField
    private  String title;

    @ForeignCollectionField(eager = false)
    private Collection<ORMImages> images;

    @ForeignCollectionField(eager = false)
    private Collection<ORMLinks> links;

    @ForeignCollectionField(eager = false)
    private Collection<ORMVideos> videos;

    @ForeignCollectionField(eager = false)
    private Collection<ORMAudios> audios;

    public ORMContent() {
    }

    public ORMContent(String description, String comment, String title) {
        this.description = description;
        this.comment = comment;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<ORMImages> getImages() {
        return images;
    }

    public void setImages(Collection<ORMImages> images) {
        this.images = images;
    }

    public Collection<ORMLinks> getLinks() {
        return links;
    }

    public void setLinks(Collection<ORMLinks> links) {
        this.links = links;
    }

    public Collection<ORMVideos> getVideos() {
        return videos;
    }

    public void setVideos(Collection<ORMVideos> videos) {
        this.videos = videos;
    }

    public Collection<ORMAudios> getAudios() {
        return audios;
    }

    public void setAudios(Collection<ORMAudios> audios) {
        this.audios = audios;
    }
}
