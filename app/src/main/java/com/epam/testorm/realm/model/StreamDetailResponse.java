package com.epam.testorm.realm.model;


import com.epam.testorm.sugar.model.NewsItemSugar;
import com.google.gson.annotations.Expose;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Mike on 16.07.13.
 */
public class StreamDetailResponse {

    @Expose
    private int total;
    @Expose
    private RealmList<NewsItemRealm> updates;

    public StreamDetailResponse() {
    }

    public StreamDetailResponse(int total, RealmList<NewsItemRealm> items) {
        this.total = total;
        this.updates = items;
    }

    public int getTotal() {
        return total;
    }

    public RealmList<NewsItemRealm> getItems() {
        return updates;
    }

}
