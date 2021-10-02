package com.example.weatherapplication.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.weatherapplication.R;
import com.example.weatherapplication.adapter.LocationAdapter;
import com.example.weatherapplication.model.location.Location;
import com.example.weatherapplication.model.location.LocationProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class LocationActivity extends AppCompatActivity implements LocationAdapter.ClickInterface{

    public static final String BASE_URL= "https://fcc-weather-api.glitch.me/";
    public static final String FULL_BASE_URL= "https://fcc-weather-api.glitch.me/api/current?lat=35&lon=139";
    private static final String TAG = "MyTag:LAct";
    private RecyclerView mRecycler;
    private LocationAdapter mAdapter;
    private SearchView mSearch;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static boolean SET_COMPLETE = false;

    public static List<Location> mSavedLocations;
    public static final String SAVE_LOCATION="saved location";
    public static final String KEY="1001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initSharedPreference();
        mSavedLocations = getList();
        initRecyclerView();
        if(mSavedLocations == null || mSavedLocations.size() == 0){
            Toast.makeText(this, "No location Saved", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Locations size : "+mSavedLocations.size(), Toast.LENGTH_SHORT).show();

        }
    }


    private void initRecyclerView(){
        mRecycler = findViewById(R.id.locationRecyclerViewId);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LocationAdapter(LocationProvider.getLocations(),this);
        mRecycler.setAdapter(mAdapter);
        searchFilter();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
        //mDisposable.clear();
        //setList(KEY,LocationProvider.getLocations());

        Log.d(TAG, "onDestroy: location changed : "+WeatherActivity.LOCATION_CHANGED);
    }
    private void initSharedPreference(){
        Log.d(TAG, "initSharedPreference: called");
        sharedPreferences = getSharedPreferences(SAVE_LOCATION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public List<Location> getList(){
        Log.d(TAG, "getList: called");
        List<Location> arrayItems = new ArrayList<>();
        String serializedObject = sharedPreferences.getString(KEY, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Location>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }

    public void setList(String key, List<Location> list) {
        Log.d(TAG, "setList: called");
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
        SET_COMPLETE = true;
    }

    public void set(String key, String value) {
        Log.d(TAG, "set: called");
        editor.putString(key, value);
        editor.commit();

    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: adapter click "+position);
    }

    @Override
    public void onLongItemClick(int position) {
        Log.d(TAG, "onItemClick: adapter long click "+position);

    }
    private void searchFilter(){
        mSearch = findViewById(R.id.searchViewId);
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: called");
        super.onStart();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState, persistentState);
    }


    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: called");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: called");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: called");
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: called");
        setList(KEY,mSavedLocations);
        super.onBackPressed();
    }
}