package com.example.weatherapplication.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.example.weatherapplication.R;
import com.example.weatherapplication.adapter.ReportAdapter;
import com.example.weatherapplication.model.location.Location;
import com.example.weatherapplication.model.weather.CustomReport;
import com.example.weatherapplication.repository.WeatherRepository;
import com.example.weatherapplication.viewmodel.WeatherViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class WeatherActivity extends AppCompatActivity implements ReportAdapter.ClickInterface {
    private static final String TAG = "MyTag:WAct:";
    private static final String HANDLER_KEY = "111222333";
    private static boolean THREAD_COMPLETE = false;
    private static boolean FRIST_RUN = false;
    private ReportAdapter mAdapter;
    private RecyclerView mRecycler;
    private List<CustomReport> mReports;
    public static List<Location> mSavedLocations;
    public static final String SAVE_LOCATION="saved location";
    public static final String KEY="1001";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private WeatherViewModel mViewModel;
    private SnapHelper mSnapHelper;
    public static boolean LOCATION_CHANGED = false;
    private GifImageView mGifImageView;
    View mParentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.d(TAG, "onCreate: called");
        FRIST_RUN = true;
        mParentLayout = findViewById(android.R.id.content);
        mGifImageView = findViewById(R.id.weatherLoadingGifId);
        mGifImageView.setImageResource(R.drawable.loading_gif);
        showLoading();
        initRecyclerView();

        if(FRIST_RUN){
            showSnackMessage("Loading Data");
            initSharedPreference();
            getLoadData();
            FRIST_RUN = false;
        }
    }

    private void showSnackMessage(String message){
        Snackbar.make(mParentLayout, message, Snackbar.LENGTH_LONG)
                .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                .setActionTextColor(getResources().getColor(android.R.color.white))
                .show();
    }

    private void getLoadData(){
        new GetData().execute();
    }

    private void showLoading(){
        Log.d(TAG, "showLoading: called");
        mGifImageView.setVisibility(View.VISIBLE);
    }
    private void hideLoading(){
        Log.d(TAG, "hideLoading: called");
        mGifImageView.setVisibility(View.GONE);
    }
    private void initRecyclerView(){
        mSnapHelper = new PagerSnapHelper();
        mReports = new ArrayList<>();
        mRecycler = findViewById(R.id.weatherRecyclerViewId);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mAdapter = new ReportAdapter(mReports,this);
        mAdapter.setmContext(this);
        mRecycler.setAdapter(mAdapter);
        mSnapHelper.attachToRecyclerView(mRecycler);
    }

    private void loadWeather(List<Location> mSavedLocations) {
        Log.d(TAG, "loadWeather: called");
        if(mViewModel == null){
            initViewModel();
        }
        mViewModel.getWeatherReports(mSavedLocations);
    }
    private void observeWeather(){
        mViewModel.mLiveListData.observe(this, reports -> {
            Log.d(TAG, "observeWeather: observer : reports "+reports.size());
            mReports = reports;
            Log.d(TAG, "observeWeather: observer : mReports "+reports.size());
            setData();
        });
    }
    private void setData(){
        Log.d(TAG, "setData: called");
        mAdapter.setmReports(mReports);
        mRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        showSnackMessage("Data showed");
        hideLoading();
    }

    private void initViewModel() {
        Log.d(TAG, "initViewModel: called");
        mViewModel= new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(WeatherViewModel.class);
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
        Log.d(TAG, "getList: size is : "+arrayItems.size());
        return arrayItems;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: called");
        if(LOCATION_CHANGED && !FRIST_RUN){
            showSnackMessage("Location changed");
            Log.d(TAG, "onStart: location changed : ");
            getLoadData();
            LOCATION_CHANGED = false;
        }
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called");
        LOCATION_CHANGED = false;
        FRIST_RUN = true;
        super.onDestroy();
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
        super.onBackPressed();
    }

    class GetData extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... activities) {
            mSavedLocations = getList();
            loadWeather(mSavedLocations);
            Log.d(TAG, "doInBackground: WeatherRepository.LIST_COMPLETE : "+WeatherRepository.LOAD_COMPLETE);
            while (!WeatherRepository.LOAD_COMPLETE){

            }
            Log.d(TAG, "doInBackground: PostRepository.GET_POST : "+WeatherRepository.LOAD_COMPLETE);
            return "null";
        }

        @Override
        protected void onPostExecute(String avoid) {
            putFinishMessageToUi();
        }
    }

    private void putFinishMessageToUi() {
        observeWeather();
    }

}