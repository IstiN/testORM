package com.epam.testorm.sugar;

import android.app.Activity;
import android.widget.ListAdapter;

import com.epam.testorm.ICacheManager;
import com.epam.testorm.gson.BaseResponse;
import com.epam.testorm.gson.MediaItem;
import com.epam.testorm.gson.StreamDetails;
import com.epam.testorm.sugar.model.AuthorSugar;
import com.epam.testorm.sugar.model.BaseSugarResponse;
import com.epam.testorm.sugar.model.ContentSugar;
import com.epam.testorm.sugar.model.MediaItemSugar;
import com.epam.testorm.sugar.model.MediaSugar;
import com.epam.testorm.sugar.model.NewsItemSugar;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;


/**
 * Created by Mikhail_Ivanou on 7/29/2015.
 */
public class SugarORMManager implements ICacheManager {


    public SugarORMManager(Activity context) {
    }

    @Override
    public void processFeed(String string) {
//        manual(string);
        gsonOnly(string);
    }

    private void gsonOnly(String string) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getAnnotation(Expose.class) == null;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        BaseSugarResponse streamDetails = gson.fromJson(string, BaseSugarResponse.class);
        List<NewsItemSugar> items = streamDetails.getData().getItems();
        SugarRecord.saveInTx(items);
    }

    private void manual(String string) {
        Gson gson = new Gson();
        BaseResponse streamDetails = gson.fromJson(string, BaseResponse.class);
        List<StreamDetails> streams = streamDetails.getData().getItems();
        List<NewsItemSugar> list = new ArrayList<>();
        for (StreamDetails item : streams) {
            list.add(processItem(item));
        }
        SugarRecord.saveInTx(list);
    }

    private NewsItemSugar processItem(StreamDetails item) {
        NewsItemSugar newsItem = new NewsItemSugar();
        newsItem.setId(item.getTimestamp());
        newsItem.setTimestamp(item.getTimestamp());
        MediaItemSugar link = new MediaItemSugar();
        link.setUrl(item.getLink());
        newsItem.setLink(link);

        AuthorSugar author = new AuthorSugar();
        author.setUserId(item.getAuthor().getId());
        author.setRef(item.getAuthor().getRef());
        author.setNetwork(item.getAuthor().getNetwork());
        author.setDisplayName(item.getAuthor().getDisplayName());

        MediaItemSugar avatar = new MediaItemSugar();
        avatar.setUrl(checkNull(item.getAuthor().getAvatar()));
        author.setAvatar(avatar);

        MediaItemSugar profile = new MediaItemSugar();
        profile.setUrl(checkNull(item.getAuthor().getProfileUrl()));
        author.setProfile(profile);
        newsItem.setAuthor(author);

        ContentSugar content = new ContentSugar();
        content.setComment(checkNull(item.getContentComment()));
        content.setTitle(checkNull(item.getContentTitle()));
        content.setDescription(checkNull(item.getContentDesc()));
        MediaSugar mediaSugar = new MediaSugar();
        StreamDetails.Media contentMedia = item.getContentMedia();
        if (contentMedia != null) {
            mediaSugar.setAudios(processMediaContent(contentMedia.getAudios()));
            mediaSugar.setVideos(processMediaContent(contentMedia.getVideos()));
            mediaSugar.setPhotos(processMediaContent(contentMedia.getPhotos()));
            mediaSugar.setLinks(processMediaContent(contentMedia.getLinks()));
        }
        content.setMedia(mediaSugar);
        newsItem.setContent(content);
        return newsItem;
    }

    private String checkNull(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

    private List<MediaItemSugar> processMediaContent(ArrayList<MediaItem> audios) {
        List<MediaItemSugar> audiosSugarList = new ArrayList<>();
        if (audios != null) {
            for (MediaItem audio : audios) {
                MediaItemSugar media = new MediaItemSugar();
                media.setTitle(checkNull(audio.getTitle()));
                media.setUrl(checkNull(audio.getUrl()));
                media.setDescription(checkNull(audio.getDescription()));
                if (audio.getImage() != null) {
                    MediaItemSugar image = new MediaItemSugar();
                    image.setTitle(checkNull(audio.getImage().getTitle()));
                    image.setUrl(checkNull(audio.getImage().getUrl()));
                    media.setImage(image);
                }
                if (audio.getThumbnailUrl() != null) {
                    MediaItemSugar image = new MediaItemSugar();
                    image.setTitle(checkNull(audio.getThumbnailUrl().getTitle()));
                    image.setUrl(checkNull(audio.getThumbnailUrl().getUrl()));
                    media.setImage(image);
                }
                audiosSugarList.add(media);
            }
        }
        return audiosSugarList;
    }


    @Override
    public ListAdapter getFullAdapter() {
        return null;
    }

    @Override
    public ListAdapter getFilteredAdapter() {
        return null;
    }

    @Override
    public ListAdapter getImagesOnlyAdapter() {
        return null;
    }
}
