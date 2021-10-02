package com.example.weatherapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.R;
import com.example.weatherapplication.model.weather.CustomReport;
import com.example.weatherapplication.utils.Utils;
import com.example.weatherapplication.view.LocationActivity;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder>{

    private List<CustomReport> mReports;
    private ClickInterface mClickInterface;
    public static Context mContext;
    public static final String TAG = "MyTag:ReportAdapter ";

    public ReportAdapter(List<CustomReport> mReports, ClickInterface mClickInterface) {
        Log.d(TAG, "ReportAdapter: called");
        this.mClickInterface = mClickInterface;
        this.mReports = mReports;
        //setHasStableIds(true);
    }

    public void setmReports(List<CustomReport> mReports) {
        this.mReports = mReports;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public int getSize(){
        return mReports.size();
    }

    @NonNull
    @Override
    public ReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.single_weather,parent,false);
        return new ReportAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        CustomReport report = mReports.get(position);
        Log.d(TAG, "onBindViewHolder: condition "+ report.condition);
        if(report.condition.toLowerCase().contains("cloud")){
            holder.layout.setBackgroundResource(R.drawable.cloudy);
            holder.iconImage.setImageResource(R.drawable.cloud_png);
            holder.gifImage.setImageResource(R.drawable.cloud_gif);
        }
        else if(report.condition.toLowerCase().contains("clear")){
            holder.layout.setBackgroundResource(R.drawable.sunny);
            holder.iconImage.setImageResource(R.drawable.sun_png);
            holder.gifImage.setImageResource(R.drawable.sun_gif);
        }
        else if(report.condition.toLowerCase().contains("haze")){
            holder.layout.setBackgroundResource(R.drawable.haze);
            holder.iconImage.setImageResource(R.drawable.haze_png);
            holder.gifImage.setImageResource(R.drawable.haze_gif);
        }
        else if(report.condition.toLowerCase().contains("rain")){
            holder.layout.setBackgroundResource(R.drawable.rain);
            holder.iconImage.setImageResource(R.drawable.rain_png);
            holder.gifImage.setImageResource(R.drawable.rain_gif);
        }
        else if(report.condition.toLowerCase().contains("snow")){
            holder.layout.setBackgroundResource(R.drawable.snow);
            holder.iconImage.setImageResource(R.drawable.snow_png);
            holder.gifImage.setImageResource(R.drawable.snow_gif);
        }
        else if(report.condition.toLowerCase().contains("fog") || report.condition.toLowerCase().contains("mist")){
            holder.layout.setBackgroundResource(R.drawable.fog);
            holder.iconImage.setImageResource(R.drawable.haze_png);
            holder.gifImage.setImageResource(R.drawable.fog_gif);
        }
        else{
            holder.layout.setBackgroundResource(R.drawable.partly_sunny);
            holder.iconImage.setImageResource(R.drawable.sun_png);
            holder.gifImage.setImageResource(R.drawable.sun_gif);
        }
        holder.conditionText.setText(Utils.makeCapital(report.condition));
        if(Utils.onlyOne(report.condition)){
            Log.d(TAG, "onBindViewHolder: not more than one : "+report.condition);
            holder.conditionText.setPadding(5,12,10,12);
            //textView.setX(10);
        }
        holder.locationText.setText(report.location);
        holder.realFeelText.setText(""+report.realTemperature+"°C");
        holder.temperatureText.setText(""+report.temperature);
        holder.windText.setText(""+report.wind+"km/hr");
        holder.detailsText.setText(report.details);
        holder.citiesText.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, LocationActivity.class));
        });
    }



    @Override
    public int getItemCount() {
        return mReports.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private TextView locationText,temperatureText,detailsText,citiesText,windText,realFeelText,conditionText;
        private ImageView iconImage;
        private ConstraintLayout layout;
        private GifImageView gifImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            locationText = itemView.findViewById(R.id.singleWeatherCityLocationTextId);
            temperatureText = itemView.findViewById(R.id.singleWeatherTemperatureTextId);
            detailsText = itemView.findViewById(R.id.singleWeatherDetailTextId);
            citiesText = itemView.findViewById(R.id.singleWeatherCityId);
            windText = itemView.findViewById(R.id.singleWeatherWindTextId);
            realFeelText = itemView.findViewById(R.id.singleWeatherActualTextId);
            layout = itemView.findViewById(R.id.singleWeatherLayoutId);
            iconImage = itemView.findViewById(R.id.singleWeatherIconImageId);
            gifImage = itemView.findViewById(R.id.singleWeatherConditionGifImageId);
            conditionText= itemView.findViewById(R.id.singleWeatherConditionTextId);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            mClickInterface.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mClickInterface.onLongItemClick(getAdapterPosition());
            return false;
        }
    }
    public interface ClickInterface {
        void onItemClick(int position);
        void onLongItemClick(int position);
    }


}


