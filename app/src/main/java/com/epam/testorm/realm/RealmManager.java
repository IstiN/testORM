package com.epam.testorm.realm;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.epam.testorm.ICacheManager;
import com.epam.testorm.R;
import com.epam.testorm.common.StringUtils;
import com.epam.testorm.gson.BaseResponse;
import com.epam.testorm.gson.Media;
import com.epam.testorm.gson.MediaItem;
import com.epam.testorm.gson.StreamDetails;
import com.epam.testorm.realm.model.AuthorRealm;
import com.epam.testorm.realm.model.ContentRealm;
import com.epam.testorm.realm.model.MediaItemRealm;
import com.epam.testorm.realm.model.MediaRealm;
import com.epam.testorm.realm.model.NewsItemRealm;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Mike on 07.07.2015.
 */
public class RealmManager implements ICacheManager {


    private Activity mActivity;

    public RealmManager(Activity context) {
        mActivity = context;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    public void processFeed(String feed) {

        Realm realm = Realm.getDefaultInstance();
//      processWithJSON(feed, realm);
//      processWithGson(feed, realm);
        processWithGson2(feed, realm);  //manual

    }

    private void processWithGson(String feed, Realm realm) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        JsonElement json = new JsonParser().parse(feed);
        JsonObject asJsonObject = json.getAsJsonObject();
        JsonObject data = asJsonObject.getAsJsonObject("data");
        JsonArray updates = data.getAsJsonArray("updates");
        Type type = new TypeToken<List<NewsItemRealm>>() {
        }.getType();
        List<NewsItemRealm> streams = gson.fromJson(updates, type);
        realm.beginTransaction();
        realm.clear(NewsItemRealm.class);
        realm.copyToRealmOrUpdate(streams);
        realm.commitTransaction();
    }

    private void processWithJSON(String feed, Realm realm) {
        JSONArray result = null;
        try {
            JSONObject main = new JSONObject(feed);
            JSONObject data = main.getJSONObject("data");
            result = data.getJSONArray("updates");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        realm.beginTransaction();
        realm.clear(NewsItemRealm.class);
        realm.createOrUpdateAllFromJson(NewsItemRealm.class, result);
        realm.commitTransaction();
    }

    private void processWithGson2(String feed, Realm realm) {
        Gson gson = new Gson();
        BaseResponse streamDetails = gson.fromJson(feed, BaseResponse.class);
        List<StreamDetails> streams = streamDetails.getData().getItems();

        RealmList<NewsItemRealm> realmList = new RealmList<>();
        for (StreamDetails item : streams) {
            realmList.add(processItem(item));
        }
        realm.beginTransaction();
        realm.clear(NewsItemRealm.class);
        realm.copyToRealmOrUpdate(realmList);
        realm.commitTransaction();
    }

    private NewsItemRealm processItem(StreamDetails item) {
        NewsItemRealm newsItem = new NewsItemRealm();
        newsItem.setTimestamp(item.getTimestamp());
        MediaItemRealm link = new MediaItemRealm();
        link.setUrl(item.getLink());
        newsItem.setLink(link);

        AuthorRealm author = new AuthorRealm();
        author.setId(item.getAuthor().getId());
        author.setRef(item.getAuthor().getRef());
        author.setNetwork(item.getAuthor().getNetwork());
        author.setDisplayName(item.getAuthor().getDisplayName());

        MediaItemRealm avatar = new MediaItemRealm();
        avatar.setUrl(checkNull(item.getAuthor().getAvatar()));
        author.setAvatar(avatar);

        MediaItemRealm profile = new MediaItemRealm();
        profile.setUrl(checkNull(item.getAuthor().getProfileUrl()));
        author.setProfile(profile);
        newsItem.setAuthor(author);

        ContentRealm content = new ContentRealm();
        content.setComment(checkNull(item.getContentComment()));
        content.setTitle(checkNull(item.getContentTitle()));
        content.setDescription(checkNull(item.getContentDesc()));
        MediaRealm mediaRealm = new MediaRealm();
        Media contentMedia = item.getContentMedia();
        if (contentMedia != null) {
            mediaRealm.setAudios(processMediaContent(contentMedia.getAudios()));
            mediaRealm.setVideos(processMediaContent(contentMedia.getVideos()));
            mediaRealm.setPhotos(processMediaContent(contentMedia.getPhotos()));
            mediaRealm.setLinks(processMediaContent(contentMedia.getLinks()));
        }
        content.setMedia(mediaRealm);
        newsItem.setContent(content);
        return newsItem;
    }

    private String checkNull(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

    private RealmList<MediaItemRealm> processMediaContent(ArrayList<MediaItem> audios) {
        RealmList<MediaItemRealm> audiosRealmList = new RealmList<>();
        if (audios != null) {
            for (MediaItem audio : audios) {
                MediaItemRealm media = new MediaItemRealm();
                media.setTitle(checkNull(audio.getTitle()));
                media.setUrl(checkNull(audio.getUrl()));
                media.setDescription(checkNull(audio.getDescription()));
                if (audio.getImage() != null) {
                    MediaItemRealm image = new MediaItemRealm();
                    image.setTitle(checkNull(audio.getImage().getTitle()));
                    image.setUrl(checkNull(audio.getImage().getUrl()));
                    media.setImage(image);
                }
                if (audio.getThumbnailUrl() != null) {
                    MediaItemRealm image = new MediaItemRealm();
                    image.setTitle(checkNull(audio.getThumbnailUrl().getTitle()));
                    image.setUrl(checkNull(audio.getThumbnailUrl().getUrl()));
                    media.setImage(image);
                }
                audiosRealmList.add(media);
            }
        }
        return audiosRealmList;
    }


    @Override
    public ListAdapter getFullAdapter() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewsItemRealm> all = realm.where(NewsItemRealm.class).findAll();
        return createAdapter(all);
    }

    @Override
    public ListAdapter getFilteredAdapter() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewsItemRealm> filtered = realm.where(NewsItemRealm.class).equalTo("author.displayName", "Lenta.Ru").or().equalTo("author.displayName", "AdMe.Ru").findAll();
        return createAdapter(filtered);
    }

    @Override
    public ListAdapter getImagesOnlyAdapter() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewsItemRealm> filtered = realm.where(NewsItemRealm.class).contains("content.media.photos.url","http").findAll();
        return createAdapter(filtered);
    }

    @NonNull
    private ListAdapter createAdapter(final RealmResults<NewsItemRealm> all) {
        RealmBaseAdapter mAdapter = new RealmBaseAdapter<NewsItemRealm>(mActivity, all, true) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item,
                            parent, false);
                }
                NewsItemRealm channel = realmResults.get(position);
                updateView(convertView, channel);
                return convertView;
            }
        };
        return mAdapter;
    }

    private void updateView(View convertView, NewsItemRealm channel) {
        ImageView posterView = (ImageView) convertView.findViewById(R.id.image);
        MediaRealm media = channel.getContent().getMedia();
        String posterUrl = null;
        String posterTitle = null;
        int mediaCount = 0;
        if (media != null) {
            RealmList<MediaItemRealm> photos = media.getPhotos();
            RealmList<MediaItemRealm> urls = media.getLinks();
            RealmList<MediaItemRealm> videos = media.getVideos();

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
        ImageLoader.getInstance().displayImage(channel.getAuthor().getAvatar().getUrl(), avatarView);
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
