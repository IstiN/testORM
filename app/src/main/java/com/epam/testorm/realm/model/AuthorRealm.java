package com.epam.testorm.realm.model;

import io.realm.RealmObject;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class AuthorRealm extends RealmObject {

    private String network;
    private String id;
    private String displayName;
    private String avatar;
    private String profile;
    private long ref;

    public AuthorRealm() {
    }

    public AuthorRealm(String network, String id, String displayName, String avatar, String profile, long ref) {
        this.network = network;
        this.id = id;
        this.displayName = displayName;
        this.avatar = avatar;
        this.profile = profile;
        this.ref = ref;
    }

    public String getNetwork() {
        return network;
    }

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
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

