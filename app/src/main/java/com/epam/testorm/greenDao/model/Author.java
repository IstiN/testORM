package com.epam.testorm.greenDao.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "AUTHOR".
 */
public class Author {

    private Long id;
    private Long ref;
    private String network;
    private String user_id;
    private String displayName;
    private String avatar;
    private String profile;

    public Author() {
    }

    public Author(Long id) {
        this.id = id;
    }

    public Author(Long id, Long ref, String network, String user_id, String displayName, String avatar, String profile) {
        this.id = id;
        this.ref = ref;
        this.network = network;
        this.user_id = user_id;
        this.displayName = displayName;
        this.avatar = avatar;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
