package com.epam.testorm.realm;

import android.content.Context;
import android.text.TextUtils;

import com.epam.testorm.gson.Channel;
import com.epam.testorm.gson.Images;
import com.epam.testorm.gson.Params;
import com.epam.testorm.gson.Responce;
import com.epam.testorm.gson.Station;
import com.epam.testorm.gson.VideoStream;
import com.google.gson.Gson;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Mike on 07.07.2015.
 */
public class RealmCache {


    public RealmCache(Context context) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public void process(String feed) {

        Gson gson = new Gson();
        Realm realm = Realm.getDefaultInstance();
        Responce channelsResponce = gson.fromJson(feed, Responce.class);
        List<Channel> channels = channelsResponce.getChannels();
        realm.beginTransaction();
        realm.clear(RealmChannel.class);
        for (Channel item : channels) {
           processItem(item, realm);
        }
        realm.commitTransaction();
    }

    private void processItem(Channel item, Realm realm) {
        RealmChannel channel = realm.createObject(RealmChannel.class);
        channel.setId(item.getId());
        channel.setChannelNumber(item.getChannelNumber());
        channel.setLanguageCode(item.getLanguageCode());
        channel.setVisible(item.isVisible());

        Params params = item.getStationSchedules().get(0);
        Station station = params.getStation();
        RealmStation realmStation = realm.createObject(RealmStation.class);

        realmStation.setId(station.getId());
        realmStation.setTitle(validate(station.getTitle()));
        realmStation.setDescription(validate(station.getDescription()));
        realmStation.setHd(station.isHd());
        realmStation.setOutOfHomeEnabled(station.isOutOfHomeEnabled());
        realmStation.setScrubbingEnabled(station.isScrubbingEnabled());
        realmStation.setServiceId(validate(station.getServiceId()));

        List<Images> images = station.getImages();
        if (images != null && images.size() > 0) {
            for (Images image : images) {
                RealmImages realmImage = realm.createObject(RealmImages.class);
                realmImage.setAssetType(image.getAssetType());
                realmImage.setUrl(image.getUrl());
                realmStation.getImages().add(realmImage);
            }
        }
        List<VideoStream> videos = station.getVideoStreams();
        if (videos != null && videos.size() > 0) {
            for (VideoStream video : videos) {
                RealmVideoStream realmVideo = realm.createObject(RealmVideoStream.class);
                realmVideo.setAssetType(video.getAssetType());
                realmVideo.setUrl(video.getUrl());
                realmVideo.setProtectionKey(video.getProtectionKey());
                realmStation.getVideoStreams().add(realmVideo);
            }
        }
        List<String> entitlements = station.getEntitlements();
        if (entitlements != null && entitlements.size() > 0) {
            for (String value : entitlements) {
                RealmEntitlement realmEntitlement = realm.createObject(RealmEntitlement.class);
                realmEntitlement.setValue(value);
                realmStation.getEntitlements().add(realmEntitlement);
            }
        }
        channel.setStationSchedules(realmStation);
    }

    private String validate(String title) {
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        return title;
    }

    public RealmResults<RealmChannel> getAllChannels() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(RealmChannel.class).findAll();
    }

    public RealmResults<RealmChannel> getVisibleChannels() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(RealmChannel.class).equalTo("visible", true).findAll();
    }

    public RealmResults<RealmChannel> getHDChannels() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(RealmChannel.class).equalTo("stationSchedules.isHd", true).findAll();
    }

    public RealmResults<RealmChannel> getVIPChannels() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(RealmChannel.class).contains("stationSchedules.entitlements.value", "VIP").findAll();
    }

    public RealmResults<RealmChannel> getLiveChannels() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(RealmChannel.class).contains("stationSchedules.videoStreams.url", "http").findAll();
    }
}
