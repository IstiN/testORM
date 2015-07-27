package com.epam.testorm.gson;


import java.util.List;

/**
 * Created by Mike on 16.07.13.
 */
public class StreamDetailResponse {

    private int total;
    private List<StreamDetails> updates;

    public StreamDetailResponse() {
    }

    public StreamDetailResponse(int total, List<StreamDetails> items) {
        this.total = total;
        this.updates = items;
    }

    public int getTotal() {
        return total;
    }

    public List<StreamDetails> getItems() {
        return updates;
    }

}
