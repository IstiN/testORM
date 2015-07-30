package com.epam.testorm.sugar.model;


import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;

import java.util.List;


/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class MediaSugar extends SugarRecord {

    @Expose
    private List<MediaItemSugar> audios;
    @Expose
    private List<MediaItemSugar> photos;
    @Expose
    private List<MediaItemSugar> videos;
    @Expose
    private List<MediaItemSugar> links;

    public MediaSugar() {
    }

    public MediaSugar(List<MediaItemSugar> audios, List<MediaItemSugar> photos, List<MediaItemSugar> videos, List<MediaItemSugar> links) {
        this.audios = audios;
        this.photos = photos;
        this.videos = videos;
        this.links = links;
    }

    public List<MediaItemSugar> getAudios() {
        return audios;
    }

    public List<MediaItemSugar> getPhotos() {
        return photos;
    }

    public List<MediaItemSugar> getVideos() {
        return videos;
    }

    public List<MediaItemSugar> getLinks() {
        return links;
    }

    public void setAudios(List<MediaItemSugar> audios) {
        this.audios = audios;
    }

    public void setPhotos(List<MediaItemSugar> photos) {
        this.photos = photos;
    }

    public void setVideos(List<MediaItemSugar> videos) {
        this.videos = videos;
    }

    public void setLinks(List<MediaItemSugar> links) {
        this.links = links;
    }
}
