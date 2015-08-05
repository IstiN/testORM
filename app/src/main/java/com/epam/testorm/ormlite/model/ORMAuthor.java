package com.epam.testorm.ormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mike on 05.08.2015.
 */

@DatabaseTable(tableName = "ORMAuthor")
public class ORMAuthor {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private Long ref;

    @DatabaseField
    private String network;

    @DatabaseField
    private String user_id;

    @DatabaseField
    private String displayName;

    @DatabaseField
    private String avatar;

    @DatabaseField
    private String profile;

    public ORMAuthor() {
    }

    public ORMAuthor(Long ref, String network, String id, String displayName, String avatar, String profile) {
        this.ref = ref;
        this.network = network;
        this.user_id = id;
        this.displayName = displayName;
        this.avatar = avatar;
        this.profile = profile;
    }

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getId() {
        return user_id;
    }

    public void setId(String id) {
        this.user_id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
