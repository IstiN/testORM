package com.epam.testorm.gson;

import java.io.Serializable;

/**
 * Created by Mikhail_Ivanou on 8/5/2015.
 */
public class Content implements Serializable {
    private Media media;

    private String description;
    private String comment;
    private String title;

    public Content() {
    }

    public Content(Media media, String description, String title, String comment) {
        this.media = media;
        this.description = description;
        this.title = title;
        this.comment = comment;
    }


    public Media getMedia() {
        return media;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }


}
