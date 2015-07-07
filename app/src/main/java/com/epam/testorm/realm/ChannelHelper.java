package com.epam.testorm.realm;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Mike on 07.07.2015.
 */
public class ChannelHelper {

    private static List<String> mImageKeys = new ArrayList<>();

    static {
        mImageKeys.add("station-logo-tva-focused");
        mImageKeys.add("station-logo-large");
        mImageKeys.add("station-logo-medium");
        mImageKeys.add("station-logo-small");
    }

    public static String getLogoUrl(RealmChannel channel) {
        RealmStation stationSchedules = channel.getStationSchedules();
        RealmList<RealmImages> images = stationSchedules.getImages();
        for (String key : mImageKeys) {
            RealmResults<RealmImages> assetType = images.where().equalTo("assetType", key).findAll();
            if (assetType != null && assetType.size() > 0) {
                String url = assetType.get(0).getUrl();
                return url;
            }
        }
        return null;
    }

    public static RealmVideoStream getStream(RealmChannel channel) {
        RealmStation stationSchedules = channel.getStationSchedules();
        RealmList<RealmVideoStream> videoStreams = stationSchedules.getVideoStreams();
        RealmResults<RealmVideoStream> assetType = videoStreams.where().equalTo("assetType", "Orion-HSS").findAll();
        if (assetType != null && assetType.size() > 0) {
            return assetType.get(0);
        }
        return null;
    }

    public static String getEntitlements(RealmChannel channel) {
        RealmStation stationSchedules = channel.getStationSchedules();
        RealmList<RealmEntitlement> entitlements = stationSchedules.getEntitlements();
        String join = TextUtils.join(",", entitlements);
        return join;
    }
}
