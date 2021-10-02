package com.example.weatherapplication.model.weather;

public class Coordinate {
    private int lon;
    private int lat;
    /*
    "lon": 139,
     "lat": 35
     */

    public Coordinate() {

    }
    public Coordinate(int lon, int lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }
}
