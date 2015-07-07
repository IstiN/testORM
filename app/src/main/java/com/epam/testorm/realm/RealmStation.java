package com.epam.testorm.realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Mike on 07.07.2015.
 */
public class RealmStation extends RealmObject {

    private long id;
    private String title;
    private String description;
    private boolean isHd;

    private RealmList<RealmVideoStream> videoStreams;
    private RealmList<RealmImages> images;

    private String serviceId;
    private RealmList<RealmEntitlement> entitlements;

    private boolean isOutOfHomeEnabled;

    private boolean isScrubbingEnabled;


    public RealmStation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHd() {
        return isHd;
    }

    public void setHd(boolean isHd) {
        this.isHd = isHd;
    }

    public RealmList<RealmVideoStream> getVideoStreams() {
        return videoStreams;
    }

    public void setVideoStreams(RealmList<RealmVideoStream> videoStreams) {
        this.videoStreams = videoStreams;
    }

    public RealmList<RealmImages> getImages() {
        return images;
    }

    public void setImages(RealmList<RealmImages> images) {
        this.images = images;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public RealmList<RealmEntitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(RealmList<RealmEntitlement> entitlements) {
        this.entitlements = entitlements;
    }

    public boolean isOutOfHomeEnabled() {
        return isOutOfHomeEnabled;
    }

    public void setOutOfHomeEnabled(boolean isOutOfHomeEnabled) {
        this.isOutOfHomeEnabled = isOutOfHomeEnabled;
    }

    public boolean isScrubbingEnabled() {
        return isScrubbingEnabled;
    }

    public void setScrubbingEnabled(boolean isScrubbingEnabled) {
        this.isScrubbingEnabled = isScrubbingEnabled;
    }
}
