package com.epam.testorm.gson;

import java.util.List;

/**
 * Created by Mike on 07.07.2015.
 */
public class Responce {

    private List<Channel> channels;

    public Responce(List<Channel> channels) {
        this.channels = channels;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
