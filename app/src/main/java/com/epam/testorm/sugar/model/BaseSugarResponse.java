package com.epam.testorm.sugar.model;


import com.google.gson.annotations.Expose;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class BaseSugarResponse {

    @Expose
    private Meta meta;

    @Expose
    private StreamDetailSugarResponse data;

    public BaseSugarResponse() {
    }

    public BaseSugarResponse(StreamDetailSugarResponse data, Meta meta) {
        this.data = data;
        this.meta = meta;
    }

    public Meta getMeta() {
        return meta;
    }

    public StreamDetailSugarResponse getData() {
        return data;
    }
}
