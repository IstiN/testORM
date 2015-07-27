package com.epam.testorm.gson;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class BaseResponse {
    private Meta meta;
    private StreamDetailResponse data;

    public BaseResponse() {
    }

    public BaseResponse(StreamDetailResponse data, Meta meta) {
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
