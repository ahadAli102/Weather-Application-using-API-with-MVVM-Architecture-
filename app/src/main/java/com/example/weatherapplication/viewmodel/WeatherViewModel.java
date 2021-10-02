package com.example.weatherapplication.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weatherapplication.model.location.Location;
import com.example.weatherapplication.model.weather.CustomReport;
import com.example.weatherapplication.model.weather.Report;
import com.example.weatherapplication.repository.WeatherRepository;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository mRepository;
    public LiveData<Report> mLiveData;
    public LiveData<List<CustomReport>> mLiveListData;
    public static final String TAG = "MyTag:ViewModel:";

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "WeatherViewModel: called");
        if(mRepository == null){
            mRepository = new WeatherRepository();
        }
    }
    /*public void getData(String url){
        mLiveData = mRepository.getData(url);
        
    }*/

    public void getWeatherReports(List<Location> mSavedLocations) {
        mLiveListData = mRepository.getWeatherReports(mSavedLocations);
    }
}
