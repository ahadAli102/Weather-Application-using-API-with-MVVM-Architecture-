package com.example.weatherapplication.model.weather;

public class Report {
    Coordinate coord;
    //List<Weather> weather;
    Weather weather;
    String base;
    Main main;
    int visibility;
    Wind wind;
    Cloud clouds;
    long dt;
    System sys;
    int timezone;
    long id;
    String name;
    int cod;

    public Report() {
    }

    public Report(Coordinate coord, Weather weather, String base, Main main, int visibility, Wind wind, Cloud clouds, long dt, System sys, int timezone, long id, String name, int cod) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.timezone = timezone;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public Weather getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public int getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Cloud getClouds() {
        return clouds;
    }

    public long getDt() {
        return dt;
    }

    public System getSys() {
        return sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }
}
