package com.epam.testorm.sugar.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class AuthorSugar extends SugarRecord {

    @Expose
    private String network;

    @Expose
    @SerializedName("id")
    private String userId;

    @Expose
    private String displayName;
    @Expose
    private String avatar;
    @Expose
    private String profile;
    @Expose
    private long ref;

    public AuthorSugar() {
    }

    public AuthorSugar(String network, String id, String displayName, String avatar, String profile, long ref) {
        this.network = network;
        this.userId = id;
        this.displayName = displayName;
        this.avatar = avatar;
        this.profile = profile;
        this.ref = ref;
    }

    public String getNetwork() {
        return network;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getProfile() {
        return profile;
    }

    public long getRef() {
        return ref;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setRef(long ref) {
        this.ref = ref;
    }
}

