package com.epam.testorm.sugar.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Mike on 20.07.13.
 */
public class Meta {

    @Expose
    private int code;
    @Expose
    private String error_message;
    @Expose
    private String status;

    public Meta() {
    }

    public Meta(int code, String error_message, String status) {
        this.code = code;
        this.error_message = error_message;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return error_message;
    }

    public String getStatus() {
        return status;
    }
}
