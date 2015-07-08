package com.epam.testorm.gson;

/**
 * Created by Mikhail_Ivanou on 1/24/14.
 */
public class Params {

    private Station station;

    public Params() {
    }

    public Params(Station station) {
        this.station = station;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
