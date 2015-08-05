package com.epam.testorm.gson;

import com.epam.testorm.common.HashUtils;

import java.io.Serializable;

/**
 * Created by Mikhail_Ivanou on 8/5/2015.
 */
public class Author implements Serializable {

    private String network;
    private String id;
    private String displayName;
    private MediaItem avatar;
    private MediaItem profile;
    private Long ref;

    public Author() {
    }

    public Author(String network, String id, String displayName, MediaItem avatar, MediaItem profile, Long ref) {
        this.network = network;
        this.id = id;
        this.displayName = displayName;
        this.avatar = avatar;
        this.profile = profile;
        this.ref = ref;
    }

    public long getKey() {
        return HashUtils.generateId(id, network);
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
        if (avatar == null) {
            return null;
        }
        return avatar.getUrl();
    }

    public String getProfileUrl() {
        if (profile == null) {
            return null;
        }
        return profile.getUrl();
    }

    public Long getRef() {
        return ref;
    }
}
