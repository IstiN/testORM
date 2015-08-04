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

    public class Author implements Serializable {
        private String network;
        private String id;
        private String displayName;
        private MediaItem avatar;
        private MediaItem profile;
        private Long ref;

        public Author() {
        }

        public Author(String network, String id, String displayName, MediaItem avatar, MediaItem profile) {
            this.network = network;
            this.id = id;
            this.displayName = displayName;
            this.avatar = avatar;
            this.profile = profile;
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

    public class Media implements Serializable {
        private ArrayList<MediaItem> photos;
        private ArrayList<MediaItem> links;
        private ArrayList<MediaItem> audios;
        private ArrayList<MediaItem> videos;

        public Media() {
        }

        public Media(ArrayList<MediaItem> photos) {
            this.photos = photos;
        }

        public ArrayList<MediaItem> getPhotos() {
            return photos;
        }


        public ArrayList<MediaItem> getLinks() {
            return links;
        }

        public ArrayList<MediaItem> getAudios() {
            return audios;
        }

        public ArrayList<MediaItem> getVideos() {
            return videos;
        }
    }


}
