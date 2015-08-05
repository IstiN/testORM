package com.epam.testorm.gson;

import com.epam.testorm.common.HashUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mike on 20.07.13.
 */
public class StreamDetails {

    private Author author;
    private Content content;

    private Long timestamp;

    private MediaItem link;


    public StreamDetails() {

    }

    public StreamDetails(Author author, Content content, Long timestamp, MediaItem link) {
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
        this.link = link;
    }

    public Author getAuthor() {
        if (author == null) {
            return new Author();
        }
        return author;
    }

    public Media getContentMedia() {
        if (content == null) {
            return null;
        }
        return content.getMedia();
    }

    public String getContentDesc() {
        if (content == null) {
            return null;
        }
        return content.getDescription();
    }

    public String getContentComment() {
        if (content == null) {
            return null;
        }
        return content.getComment();
    }

    public String getContentTitle() {
        if (content == null) {
            return null;
        }
        return content.getTitle();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getLink() {
        if (link == null) return null;
        return link.getUrl();
    }

    public MediaItem getLinkItem() {
        return link;
    }



}
