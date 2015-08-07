package com.epam.testorm.greenDao.model;

import com.epam.testorm.greenDao.model.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "IMAGES".
 */
public class Images {

    private Long id;
    private String url;
    private String title;
    private String image;
    private String original;
    private String thumbnail;
    private String description;
    private Long contentId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ImagesDao myDao;

    private Content content;
    private Long content__resolvedKey;


    public Images() {
    }

    public Images(Long id) {
        this.id = id;
    }

    public Images(Long id, String url, String title, String image, String original, String thumbnail, String description, Long contentId) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.image = image;
        this.original = original;
        this.thumbnail = thumbnail;
        this.description = description;
        this.contentId = contentId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getImagesDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    /** To-one relationship, resolved on first access. */
    public Content getContent() {
        Long __key = this.contentId;
        if (content__resolvedKey == null || !content__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContentDao targetDao = daoSession.getContentDao();
            Content contentNew = targetDao.load(__key);
            synchronized (this) {
                content = contentNew;
            	content__resolvedKey = __key;
            }
        }
        return content;
    }

    public void setContent(Content content) {
        synchronized (this) {
            this.content = content;
            contentId = content == null ? null : content.getId();
            content__resolvedKey = contentId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}