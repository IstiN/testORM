package com.epam.testorm.realm.model;


import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class MediaRealm extends RealmObject{

    private RealmList<MediaItemRealm> audios;
    private RealmList<MediaItemRealm> photos;
    private RealmList<MediaItemRealm> videos;
    private RealmList<MediaItemRealm> links;

    public MediaRealm() {
    }

    public MediaRealm(RealmList<MediaItemRealm> audios, RealmList<MediaItemRealm> photos, RealmList<MediaItemRealm> videos, RealmList<MediaItemRealm> links) {
        this.audios = audios;
        this.photos = photos;
        this.videos = videos;
        this.links = links;
    }

    public RealmList<MediaItemRealm> getAudios() {
        return audios;
    }

    public RealmList<MediaItemRealm> getPhotos() {
        return photos;
    }

    public RealmList<MediaItemRealm> getVideos() {
        return videos;
    }

    public RealmList<MediaItemRealm> getLinks() {
        return links;
    }

    public void setAudios(RealmList<MediaItemRealm> audios) {
        this.audios = audios;
    }

    public void setPhotos(RealmList<MediaItemRealm> photos) {
        this.photos = photos;
    }

    public void setVideos(RealmList<MediaItemRealm> videos) {
        this.videos = videos;
    }

    public void setLinks(RealmList<MediaItemRealm> links) {
        this.links = links;
    }
}
