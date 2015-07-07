package com.epam.testorm.gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 07.07.2015.
 */
public class Station  {

    private long id;
    private String title;
    private String description;
    private boolean isHd;

    private ArrayList<VideoStream> videoStreams;
    private ArrayList<Images> images;

    private String serviceId;
    private ArrayList<String> entitlements;

    private boolean isOutOfHomeEnabled;

    private boolean isScrubbingEnabled;


    public Station() {
    }

    public Station(long id, String title, String description, boolean isHd, ArrayList<VideoStream> videoStreams, ArrayList<Images> images, String serviceId, ArrayList<String> entitlements, boolean isOutOfHomeEnabled, boolean isScrubbingEnabled) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isHd = isHd;
        this.videoStreams = videoStreams;
        this.images = images;
        this.serviceId = serviceId;
        this.entitlements = entitlements;
        this.isOutOfHomeEnabled = isOutOfHomeEnabled;
        this.isScrubbingEnabled = isScrubbingEnabled;
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

    public List<VideoStream> getVideoStreams() {
        return videoStreams;
    }

    public void setVideoStreams(ArrayList<VideoStream> videoStreams) {
        this.videoStreams = videoStreams;
    }

    public ArrayList<Images> getImages() {
        return images;
    }

    public void setImages(ArrayList<Images> images) {
        this.images = images;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public List<String> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(ArrayList<String> entitlements) {
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
