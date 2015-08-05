package com.epam.testorm.gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mikhail_Ivanou on 8/5/2015.
 */
public class Media implements Serializable {
    private ArrayList<MediaItem> photos;
    private ArrayList<MediaItem> links;
    private ArrayList<MediaItem> audios;
    private ArrayList<MediaItem> videos;

    public Media() {
    }

    public Media(ArrayList<MediaItem> photos) {
        this.photos = photos;
    }

    public Media(ArrayList<MediaItem> photos, ArrayList<MediaItem> links, ArrayList<MediaItem> audios, ArrayList<MediaItem> videos) {
        this.photos = photos;
        this.links = links;
        this.audios = audios;
        this.videos = videos;
    }

    public ArrayList<MediaItem> getPhotos() {
        return photos;
    }


    public ArrayList<MediaItem> getLinks() {
        return links;
    }

    public ArrayList<MediaItem> getAudios() {
        return audios;
    }

    public ArrayList<MediaItem> getVideos() {
        return videos;
    }
}
