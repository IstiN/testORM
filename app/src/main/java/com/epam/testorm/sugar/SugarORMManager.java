package com.epam.testorm.sugar;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.epam.testorm.ICacheManager;
import com.epam.testorm.R;
import com.epam.testorm.common.AbstractListAdapter;
import com.epam.testorm.common.StringUtils;
import com.epam.testorm.gson.BaseResponse;
import com.epam.testorm.gson.Media;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orm.SugarContext;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mikhail_Ivanou on 7/29/2015.
 */
public class SugarORMManager implements ICacheManager {


    private Activity mActivity;

    public SugarORMManager(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void processFeed(String string) {
        SugarRecord.deleteAll(NewsItemSugar.class);
        manual(string);
    }

    //doesn't work as we need to simplify model
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
        newsItem.setLink(item.getLink());

        AuthorSugar author = new AuthorSugar();
        author.setUserId(item.getAuthor().getId());
        author.setRef(item.getAuthor().getRef());
        author.setNetwork(item.getAuthor().getNetwork());
        author.setDisplayName(item.getAuthor().getDisplayName());
        author.setAvatar(checkNull(item.getAuthor().getAvatar()));
        author.setProfile(checkNull(item.getAuthor().getProfileUrl()));
        newsItem.setAuthor(author);

        ContentSugar content = new ContentSugar();
        content.setComment(checkNull(item.getContentComment()));
        content.setTitle(checkNull(item.getContentTitle()));
        content.setDescription(checkNull(item.getContentDesc()));
        MediaSugar mediaSugar = new MediaSugar();

        Media contentMedia = item.getContentMedia();
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
        List<NewsItemSugar> all = NewsItemSugar.find(NewsItemSugar.class, null, null);
        return createAdapter((ArrayList)all);
    }

    @Override
    public ListAdapter getFilteredAdapter() {
        List<NewsItemSugar> all = NewsItemSugar.find(NewsItemSugar.class, null, null);
        return createAdapter((ArrayList)all);
    }

    @Override
    public ListAdapter getImagesOnlyAdapter() {
        List<NewsItemSugar> all = NewsItemSugar.find(NewsItemSugar.class, null, null);
        return createAdapter((ArrayList)all);
    }

    @NonNull
    private ListAdapter createAdapter(final ArrayList<NewsItemSugar> all) {
        BaseAdapter adapter = new AbstractListAdapter<NewsItemSugar>(mActivity, R.layout.item, all) {

            @Override
            protected void initView(View convertView, NewsItemSugar item, int position) {
                updateView(convertView, item);
            }
        };
        return adapter;
    }

    private void updateView(View convertView, NewsItemSugar channel) {
        ImageView posterView = (ImageView) convertView.findViewById(R.id.image);
        MediaSugar media = channel.getContent().getMedia();
        String posterUrl = null;
        String posterTitle = null;
        int mediaCount = 0;
        if (media != null) {
            List<MediaItemSugar> photos = media.getPhotos();
            List<MediaItemSugar> urls = media.getLinks();
            List<MediaItemSugar> videos = media.getVideos();

            if (photos != null && photos.size() > 0) {
                posterUrl = photos.get(0).getUrl();
                posterTitle = photos.get(0).getTitle();
                mediaCount = photos.size();
            } else if (urls != null && urls.size() > 0 && urls.get(0).getImage() != null) {
                posterUrl = urls.get(0).getImage().getUrl();
                posterTitle = urls.get(0).getUrl();
            } else if (videos != null && videos.size() > 0 && videos.get(0).getThumbnail() != null) {
                posterUrl = videos.get(0).getThumbnail().getUrl();
                posterTitle = videos.get(0).getDescription();
            }
            if (urls != null && urls.size() > 0) {
                mediaCount += urls.size();
            }
            if (videos != null && videos.size() > 0) {
                mediaCount += videos.size();
            }
        }
        ImageLoader.getInstance().displayImage(posterUrl, posterView);
        ImageView avatarView = (ImageView) convertView.findViewById(R.id.avatar);
        ImageLoader.getInstance().displayImage(channel.getAuthor().getAvatar(), avatarView);
        setText(convertView, R.id.title, channel.getAuthor().getDisplayName());
        setText(convertView, R.id.desc, StringUtils.isEmpty(channel.getContent().getTitle()) ? channel.getContent().getDescription() : channel.getContent().getTitle());

        setText(convertView, R.id.videoUrl, posterTitle);
        setText(convertView, R.id.videoKey, String.valueOf(mediaCount));
        setText(convertView, R.id.entitlements, channel.getContent().getComment());


    }

    private void setText(View view, int id, String value) {
        TextView number = (TextView) view.findViewById(id);
        if (!TextUtils.isEmpty(value)) {
            number.setText(value);
            number.setVisibility(View.VISIBLE);
        } else {
            number.setVisibility(View.GONE);
        }
    }

}
