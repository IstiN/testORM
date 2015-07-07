package com.epam.testorm.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Mike on 07.07.2015.
 */
public class RealmChannel extends RealmObject {

    @PrimaryKey
    private long id;
    private String languageCode;
    private long channelNumber;
    private boolean visible;
    private RealmStation stationSchedules;

    public RealmChannel() {
    }

    public RealmChannel(long id, String languageCode, long channelNumber, boolean visible, RealmStation stationSchedules) {
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

    public RealmStation getStationSchedules() {
        return stationSchedules;
    }

    public void setStationSchedules(RealmStation stationSchedules) {
        this.stationSchedules = stationSchedules;
    }
}
