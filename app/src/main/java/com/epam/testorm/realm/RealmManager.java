package com.epam.testorm.realm;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.epam.testorm.ICacheManager;
import com.epam.testorm.R;
import com.epam.testorm.StringUtils;
import com.epam.testorm.gson.BaseResponse;
import com.epam.testorm.gson.StreamDetails;
import com.epam.testorm.realm.model.AuthorRealm;
import com.epam.testorm.realm.model.MediaItemRealm;
import com.epam.testorm.realm.model.MediaRealm;
import com.epam.testorm.realm.model.NewsItemRealm;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Mike on 07.07.2015.
 */
public class RealmManager implements ICacheManager {


    private Context mContext;

    public RealmManager(Context context) {
        mContext = context;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    public void processFeed(String feed) {

        Gson gson = new Gson();
        Realm realm = Realm.getDefaultInstance();
        BaseResponse channelsResponce = gson.fromJson(feed, BaseResponse.class);
        List<StreamDetails> streams = channelsResponce.getData().getItems();
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
//        for (StreamDetails item : streams) {
//           processItem(item, realm);
//        }
        realm.commitTransaction();
    }

    @Override
    public ListAdapter getFullAdapter() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewsItemRealm> all = realm.where(NewsItemRealm.class).findAll();
        RealmBaseAdapter mAdapter = new RealmBaseAdapter<NewsItemRealm>(mContext, all, true) {
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

    @Override
    public ListAdapter getFilteredAdapter() {
        return null;
    }

    private void processItem(StreamDetails item, Realm realm) {
        NewsItemRealm newsItem = realm.createObject(NewsItemRealm.class);
//        newsItem.setId(item.getTimestamp());
        newsItem.setTimestamp(item.getTimestamp());
        MediaItemRealm link = realm.createObject(MediaItemRealm.class);
        link.setUrl(item.getLink());
        newsItem.setLink(link);

        AuthorRealm author = realm.createObject(AuthorRealm.class);
        author.setId(item.getAuthor().getId());
        author.setRef(item.getAuthor().getRef());
        author.setNetwork(item.getAuthor().getNetwork());
        author.setDisplayName(item.getAuthor().getDisplayName());

        MediaItemRealm avatar = realm.createObject(MediaItemRealm.class);
        avatar.setUrl(item.getAuthor().getAvatar());
        author.setAvatar(avatar);

        MediaItemRealm profile = realm.createObject(MediaItemRealm.class);
        profile.setUrl(item.getAuthor().getProfileUrl());
        author.setProfile(profile);
        newsItem.setAuthor(author);

//        //process content
//        ContentRealm content = realm.createOrUpdateAllFromJson();
//                channel.setChannelNumber(item.getChannelNumber());
//        channel.setLanguageCode(item.getLanguageCode());
//        channel.setVisible(item.isVisible());
//
//        Params params = item.getStationSchedules().get(0);
//        Station station = params.getStation();
//        RealmStation realmStation = realm.createObject(RealmStation.class);
//
//        realmStation.setId(station.getId());
//        realmStation.setTitle(validate(station.getTitle()));
//        realmStation.setDescription(validate(station.getDescription()));
//        realmStation.setHd(station.isHd());
//        realmStation.setOutOfHomeEnabled(station.isOutOfHomeEnabled());
//        realmStation.setScrubbingEnabled(station.isScrubbingEnabled());
//        realmStation.setServiceId(validate(station.getServiceId()));
//
//        List<Images> images = station.getImages();
//        if (images != null && images.size() > 0) {
//            for (Images image : images) {
//                RealmImages realmImage = realm.createObject(RealmImages.class);
//                realmImage.setAssetType(image.getAssetType());
//                realmImage.setUrl(image.getUrl());
//                realmStation.getImages().add(realmImage);
//            }
//        }
//        List<VideoStream> videos = station.getVideoStreams();
//        if (videos != null && videos.size() > 0) {
//            for (VideoStream video : videos) {
//                RealmVideoStream realmVideo = realm.createObject(RealmVideoStream.class);
//                realmVideo.setAssetType(video.getAssetType());
//                realmVideo.setUrl(video.getUrl());
//                realmVideo.setProtectionKey(video.getProtectionKey());
//                realmStation.getVideoStreams().add(realmVideo);
//            }
//        }
//        List<String> entitlements = station.getEntitlements();
//        if (entitlements != null && entitlements.size() > 0) {
//            for (String value : entitlements) {
//                RealmEntitlement realmEntitlement = realm.createObject(RealmEntitlement.class);
//                realmEntitlement.setValue(value);
//                realmStation.getEntitlements().add(realmEntitlement);
//            }
//        }
//        channel.setStationSchedules(realmStation);
    }

    private String validate(String title) {
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        return title;
    }

//    public RealmResults<RealmChannel> getAllChannels() {
//        Realm realm = Realm.getDefaultInstance();
//        return realm.where(RealmChannel.class).findAll();
//    }
//
//    public RealmResults<RealmChannel> getVisibleChannels() {
//        Realm realm = Realm.getDefaultInstance();
//        return realm.where(RealmChannel.class).equalTo("visible", true).findAll();
//    }
//
//    public RealmResults<RealmChannel> getHDChannels() {
//        Realm realm = Realm.getDefaultInstance();
//        return realm.where(RealmChannel.class).equalTo("stationSchedules.isHd", true).findAll();
//    }
//
//    public RealmResults<RealmChannel> getVIPChannels() {
//        Realm realm = Realm.getDefaultInstance();
//        return realm.where(RealmChannel.class).contains("stationSchedules.entitlements.value", "VIP").findAll();
//    }
//
//    public RealmResults<RealmChannel> getLiveChannels() {
//        Realm realm = Realm.getDefaultInstance();
//        return realm.where(RealmChannel.class).contains("stationSchedules.videoStreams.url", "http").findAll();
//    }
}
