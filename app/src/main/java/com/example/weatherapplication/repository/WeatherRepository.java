package com.example.weatherapplication.repository;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherapplication.model.location.Location;
import com.example.weatherapplication.model.weather.CustomReport;
import com.example.weatherapplication.model.weather.Main;
import com.example.weatherapplication.model.weather.Weather;
import com.example.weatherapplication.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherRepository {

    public static final String TAG = "MyTag:Repository:";
    private MutableLiveData<CustomReport> mLiveData;
    private MutableLiveData<List<CustomReport>> mLiveListData;
    private List<String> mUrls;
    private String mUrl;
    private WeatherDataCall mCall;
    public static final String BASE_URL= "https://fcc-weather-api.glitch.me/api/current?";
    private CustomReport mReport;
    private List<CustomReport> mReports;
    public static boolean LIST_COMPLETE = false;
    public static boolean LOAD_COMPLETE = false;

    /*public MutableLiveData<CustomReport> getData(String url){
        Log.d(TAG, "getData: called");
        LIST_COMPLETE = false;
        mReport = new CustomReport();
        mLiveData = new MutableLiveData<>();
        mUrl=url;
        if( mCall==null ){
            mCall = new WeatherDataCall();
        }
        mCall.execute();
        Log.d(TAG, "getData: complete is "+LIST_COMPLETE);
        while(!LIST_COMPLETE){

        }
        Log.d(TAG, "getData: complete is "+LIST_COMPLETE);

        mLiveData.postValue(mReport);
        return mLiveData;


    }*/
    /*public MutableLiveData<List<CustomReport>> getListData(List<String> urls){
        mLiveListData = new MutableLiveData<>();
        if(mCall==null){
            mCall = new WeatherDataCall();
        }


        return mLiveListData;
    }*/

    public MutableLiveData<List<CustomReport>> getWeatherReports(List<Location> mSavedLocations){
        Log.d(TAG, "getWeatherReports: called");
        LOAD_COMPLETE = false;
        mUrls = new ArrayList<>();
        mLiveListData = new MutableLiveData<>();
        mCall = new WeatherDataCall();
        String url;

        for (int i = 0; i < mSavedLocations.size(); i++) {
            url = BASE_URL+"lat="+mSavedLocations.get(i).latitude+"&lon="+mSavedLocations.get(i).longitude+"+"+
                    mSavedLocations.get(i).city+"+"+mSavedLocations.get(i).country;
            Log.d(TAG, "getWeatherReports: url : "+url);
            mUrls.add(url);
        }
        LIST_COMPLETE = false;
        mCall.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        Log.d(TAG, "getData: complete is "+LIST_COMPLETE);
        while(!LIST_COMPLETE){

        }
        Log.d(TAG, "getData: complete is "+LIST_COMPLETE);
        Log.d(TAG, "getWeatherReports: report size : "+mReports.size());
        mLiveListData.postValue(mReports);
        LOAD_COMPLETE = true;
        return mLiveListData;
    }

    class WeatherDataCall extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: called");
            mReports = new ArrayList<>();
            String jsonData = "";
            URL url;
            int count=0;
            for (String u: mUrls ) {
                count++;
                String splitChar = "\\+";
                String[] link = u.split(splitChar);
                HttpURLConnection urlConnection = null;
                try{
                    url = new URL(link[0]);
                    Log.d(TAG, "doInBackground: link : "+link[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    Log.d(TAG, "doInBackground: url Connection : "+urlConnection.getURL().toString());
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);
                    int data = reader.read();
                    while (data!=-1){
                        jsonData += (char)data;
                        data = reader.read();
                    }

                    Log.d(TAG, "doInBackground: "+jsonData);
                    //parsing json data

                    JSONObject jsonObject = new JSONObject(jsonData);
                    //JSONObject coord = jsonObject.getJSONObject("coord");
                    JSONObject jsonWind = jsonObject.getJSONObject("wind");
                    //JSONObject jsonClouds = jsonObject.getJSONObject("clouds");
                    //JSONObject jsonSystem = jsonObject.getJSONObject("sys");
                    JSONObject jsonWeather = jsonObject.getJSONArray("weather").getJSONObject(0);
                    JSONObject jsonMain = jsonObject.getJSONObject("main");
                    Main main = new Main();
                    main.setTemp(jsonMain.getDouble("temp"));
                    try{
                        main.setFeels_like(jsonMain.getDouble("feels_like"));
                    }catch (Exception e ){
                        main.setFeels_like(main.getTemp()+1);

                    }

                    main.setTemp_min(jsonMain.getDouble("temp_min"));
                    main.setTemp_max(jsonMain.getDouble("temp_max"));
                    main.setPressure(jsonMain.getInt("pressure"));
                    main.setHumidity(jsonMain.getInt("humidity"));
                    //main.setSea_level(jsonMain.getInt("sea_level"));
                    //main.setGrnd_level(jsonMain.getInt("grnd_level"));

                    //Coordinate coordinate = new Coordinate(coord.getInt("lon"),(coord.getInt("lat")));

                    Weather weather = new Weather();
                    weather.setDescription(jsonWeather.getString("description"));
                    weather.setId(jsonWeather.getInt("id"));
                    weather.setMain("main");
                    weather.setIcon("icon");

                    /*Cloud cloud = new Cloud(jsonClouds.getInt("all"));

                    Wind wind = new Wind(jsonWind.getDouble("speed"),
                            jsonWind.getDouble("deg"),jsonWind.getDouble("gust"));*/


                    /*String base = jsonObject.getString("base");
                    int visibility = jsonObject.getInt("visibility");
                    long dt = jsonObject.getLong("dt");
                    int timeZone = jsonObject.getInt("timezone");
                    long id = jsonObject.getLong("id");
                    String name = jsonObject.getString("name");
                    int cod = jsonObject.getInt("cod");*/

                    Log.d(TAG, "doInBackground: done : "+main.getTemp());
                    Log.d(TAG, "doInBackground: done : "+main.getHumidity());

                    CustomReport customReport = new CustomReport();
                    customReport.temperature = main.getTemp();
                    customReport.location = link[1]+","+link[2];
                    customReport.humidity = main.getHumidity();
                    customReport.wind = jsonWind.getDouble("speed");
                    customReport.realTemperature = main.getFeels_like();
                    if(link[0].equalsIgnoreCase("Dhaka")){
                        customReport.condition = "haze";
                        Log.d(TAG, "doInBackground: "+link[0]);
                        Log.d(TAG, "doInBackground: "+customReport.condition);
                    }
                    else{
                        customReport.condition = weather.getDescription();
                    }
                    customReport.details = "Condition: "+ Utils.makeCapital(weather.getDescription(),1) +
                            "\n"+"Maximum Temperature : "+main.getTemp_max()+
                            "\n"+"Minimum Temperature : "+main.getTemp_min()+
                            "\n"+"Humidity : "+main.getHumidity();

                    Log.d(TAG, "doInBackground: done : "+customReport.location+" "+customReport.temperature+" "+customReport.condition);
                    mReports.add(customReport);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Log.w(TAG, "doInBackground: ",e);
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.w(TAG, "doInBackground: ",e);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w(TAG, "doInBackground: ",e);
                }
                finally {
                    jsonData = "";
                    if(urlConnection!=null){
                        urlConnection.disconnect();
                        Log.d(TAG, "doInBackground: urlConnection.disconnect()");
                    }
                }
            }
            LIST_COMPLETE = true;

            return jsonData;
        }

        @Override
        protected void onPostExecute(String jsonData) {
            LIST_COMPLETE = true;
            Log.d(TAG, "onPostExecute: called");
        }
    }

    
    
}
