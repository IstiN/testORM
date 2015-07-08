package com.epam.testorm.gson;

import java.util.ArrayList;
import java.util.List;

import io.realm.annotations.PrimaryKey;

/**
 * Created by Mike on 07.07.2015.
 */
public class Channel {

    @PrimaryKey
    private long id;
    private String languageCode;
    private long channelNumber;
    private boolean visible;

    private ArrayList<Params> stationSchedules;

    public Channel() {
    }

    public Channel(long id, String languageCode, long channelNumber, boolean visible, ArrayList<Params> stationSchedules) {
        this.id = id;
        this.languageCode = languageCode;
        this.channelNumber = channelNumber;
        this.visible = visible;
        this.stationSchedules = stationSchedules;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public long getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(long channelNumber) {
        this.channelNumber = channelNumber;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<Params> getStationSchedules() {
        return stationSchedules;
    }

    public void setStationSchedules(ArrayList<Params> stationSchedules) {
        this.stationSchedules = stationSchedules;
    }
}
