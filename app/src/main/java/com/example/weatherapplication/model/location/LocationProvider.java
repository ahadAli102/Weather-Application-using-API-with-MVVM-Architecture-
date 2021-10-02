package com.example.weatherapplication.model.location;

import java.util.ArrayList;
import java.util.List;

public class LocationProvider {

    public static List<Location> mLocations;

    public static List<Location> getLocations(){
        mLocations = new ArrayList<>();
        //Rain // Clouds
        mLocations.add(new Location(23.7276,90.4106,"Dhaka","BD"));
        mLocations.add(new Location(24.3745,88.6042,"Rajshahi","BD"));
        mLocations.add(new Location(25.7439,89.2752,"Rangpur","BD"));
        mLocations.add(new Location(22.7010,90.3535,"Barishal","BD"));
        mLocations.add(new Location(22.8456,89.5403,"Khulna","BD"));
        mLocations.add(new Location(22.3569,91.7832,"Chittagong","BD"));
        mLocations.add(new Location(23.1778,89.1801,"Jashore","BD"));
        mLocations.add(new Location(24.8949,91.8687,"Sylhet","BD"));
        mLocations.add(new Location(28.7080,77.2055,"Delhi","IN"));
        mLocations.add(new Location(19.0760,72.8777,"Mumbai","IN"));
        mLocations.add(new Location(22.5726,88.3639,"Kolkata","IN"));
        mLocations.add(new Location(27.0238,74.2179,"Rajasthan","IN"));
        mLocations.add(new Location(26.2006,92.9376,"Assam","IN"));

        mLocations.add(new Location(48.1351,11.5820,"Munich","Germany"));
        mLocations.add(new Location(52.5200,13.4050,"Berlin","Germany"));
        mLocations.add(new Location(51.0504,13.7373,"Dresden","Germany"));
        mLocations.add(new Location(53.5511,9.9937,"Hamburg","Germany"));
        mLocations.add(new Location(50.9375,6.9603,"Cologne","Germany"));
        mLocations.add(new Location(50.1109,8.6821,"Frankfurt","Germany"));

        mLocations.add(new Location(59.3293,18.0686,"Stockholm","Sweden"));
        mLocations.add(new Location(55.5600,13.0247,"Malmo","Sweden"));
        mLocations.add(new Location(57.7089,11.9746,"Gothenburg","Sweden"));


        mLocations.add(new Location(59.9139,10.7522,"Oslo","Norway"));
        mLocations.add(new Location(60.3913,5.3221,"Bergen","Norway"));
        mLocations.add(new Location(69.6492,18.9553,"Tromso","Norway"));

        mLocations.add(new Location(60.1699,24.9384,"Helsinki","Finland"));
        mLocations.add(new Location(61.4978,23.7610,"Tampere","Finland"));


        return mLocations;

    }
}
