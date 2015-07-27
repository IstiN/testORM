package com.epam.testorm.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public class NewsItemRealm extends RealmObject {

    private ContentRealm content;

    @PrimaryKey
    private long timestamp;
    private AuthorRealm author;
    private MediaItemRealm link;

    public NewsItemRealm() {
    }

    public NewsItemRealm(AuthorRealm author, ContentRealm content, long timestamp, MediaItemRealm link) {
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
        this.link = link;
    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public AuthorRealm getAuthor() {
        return author;
    }

    public void setAuthor(AuthorRealm author) {
        this.author = author;
    }

    public ContentRealm getContent() {
        return content;
    }

    public void setContent(ContentRealm content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public MediaItemRealm getLink() {
        return link;
    }

    public void setLink(MediaItemRealm link) {
        this.link = link;
    }


}
