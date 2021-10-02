package com.example.weatherapplication.model.weather;

public class Wind {

    private double speed;
    private double deg;
    private double gust;

    /*
    "speed": 3.93,
    "deg": 83,
    "gust": 4.52
     */

    public Wind() {

    }
    public Wind(double speed, double deg, double gust) {
        this.speed = speed;
        this.deg = deg;
        this.gust = gust;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

    public double getGust() {
        return gust;
    }

    public void setGust(double gust) {
        this.gust = gust;
    }
}
