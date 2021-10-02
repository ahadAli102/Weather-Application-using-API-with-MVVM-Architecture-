package com.example.weatherapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.weatherapplication.R;

public class SplashActivity extends AppCompatActivity {
    public static final String TAG = "MyTag:SAct:";
    private static final int SPLASH_TIME = 2 * 1000;// 3 seconds delay
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate: splash activity");
        Toast.makeText(this, "Splash Activity", Toast.LENGTH_SHORT).show();
        try {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, WeatherActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_TIME);
        }
        catch(Exception e){

        }
    }
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}