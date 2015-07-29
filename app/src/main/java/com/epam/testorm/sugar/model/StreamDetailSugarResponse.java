package com.epam.testorm.sugar.model;


import com.epam.testorm.gson.StreamDetails;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Mike on 16.07.13.
 */
public class StreamDetailSugarResponse {

    @Expose
    private int total;
    @Expose
    private List<NewsItemSugar> updates;

    public StreamDetailSugarResponse() {
    }

    public StreamDetailSugarResponse(int total, List<NewsItemSugar> items) {
        this.total = total;
        this.updates = items;
    }

    public int getTotal() {
        return total;
    }

    public List<NewsItemSugar> getItems() {
        return updates;
    }

}
