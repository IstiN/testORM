package com.epam.testorm.gson;

/**
 * Created by Mike on 07.07.2015.
 */
public class Images  {

    private String assetType;
    private String url;

    public Images() {
    }

    public Images(String assetType, String url) {
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
