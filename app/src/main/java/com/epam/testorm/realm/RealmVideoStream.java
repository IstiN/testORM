package com.epam.testorm.realm;

import io.realm.RealmObject;

/**
 * Created by Mike on 07.07.2015.
 */
public class RealmVideoStream extends RealmObject {

    private String assetType;
    private String url;
    private String protectionKey;

    public RealmVideoStream() {
    }

    public RealmVideoStream(String assetType, String url, String protectionKey) {
        this.assetType = assetType;
        this.url = url;
        this.protectionKey = protectionKey;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtectionKey() {
        return protectionKey;
    }

    public void setProtectionKey(String protectionKey) {
        this.protectionKey = protectionKey;
    }
}
