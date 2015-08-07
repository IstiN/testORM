package com.epam.testorm.realm.model;


import com.google.gson.annotations.Expose;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class BaseRealmResponse {

    @Expose
    private Meta meta;

    @Expose
    private StreamDetailResponse data;

    public BaseRealmResponse() {
    }

    public BaseRealmResponse(StreamDetailResponse data, Meta meta) {
        this.data = data;
        this.meta = meta;
    }

    public Meta getMeta() {
        return meta;
    }

    public StreamDetailResponse getData() {
        return data;
    }
}
