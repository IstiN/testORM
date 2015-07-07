package com.epam.testorm.realm;

import io.realm.RealmObject;

/**
 * Created by Mike on 07.07.2015.
 */
public class RealmImages extends RealmObject {

    private String assetType;
    private String url;

    public RealmImages() {
    }

    public RealmImages(String assetType, String url) {
        this.assetType = assetType;
        this.url = url;
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
}
