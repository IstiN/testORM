package com.epam.testorm;

import java.util.List;

/**
 * Created by Mike on 07.07.2015.
 */
public interface IChannel {

    long getChannelId();
    String getLanguageCode();
    long getChannelNumber();
    boolean isVisible();

    //next fields are from stationSchedules/station[0]

    long getStationId();
    String getTitle();
    String getDescription();
    boolean isHD();
    String getVideoStreamUrl(); // videoStreams -> where "assetType": "Orion-HSS"
    String getProtectionKey(); // videoStreams -> where "assetType": "Orion-HSS"

    String getChannelLogo(); // images -> if exist -> "station-logo-tva-focused", then "station-logo-large" and so on
    String getServiceId();
    List<String> getEntitlements();
    boolean isOutOfHomeEnabled();
    boolean isScrubbingEnabled();

    //select all channels
    //select all visible channels
    //select all hd channels
    //select all vip entitled channels

}
