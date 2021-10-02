package com.example.weatherapplication.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.R;
import com.example.weatherapplication.model.location.Location;
import com.example.weatherapplication.view.LocationActivity;
import com.example.weatherapplication.view.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder>{

    private List<Location> mLocations;
    private List<Location> mMainLocations;
    public ClickInterface mClickInterface;
    public static final String TAG = "MyTag:LocationAdapter ";
    public static int EXIST_POSITION = 0;

    public LocationAdapter(List<Location> mLocations, ClickInterface mClickInterface) {
        Log.d(TAG, "LocationAdapter: called");
        this.mClickInterface = mClickInterface;
        this.mLocations = mLocations;
        this.mMainLocations = new ArrayList<>(mLocations);
        //setHasStableIds(true);
    }

    public void setmLocations(List<Location> mLocations) {
        this.mLocations = mLocations;
    }
    public int getSize(){
        return mLocations.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.single_location_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        //holder.setIsRecyclable(false);
        holder.textView.setText(mLocations.get(position).city+","+mLocations.get(position).country);
        if(exist(LocationActivity.mSavedLocations, mLocations.get(position))){
            // if saved location exist then add remove icon
            holder.imageView.setImageResource(R.drawable.ic_remove);
        }
        else{
            // if saved location is not exist in
            holder.imageView.setImageResource(R.drawable.ic_add);
        }
        holder.imageView.setOnClickListener(v -> {
            WeatherActivity.LOCATION_CHANGED = true;
            EXIST_POSITION = LocationActivity.mSavedLocations.size();
           try{
               if( exist(LocationActivity.mSavedLocations, mLocations.get(position)) ){
                   // if exist then remove
                   LocationActivity.mSavedLocations.remove(EXIST_POSITION);
                   holder.imageView.setImageResource(R.drawable.ic_add);
               }
               else{
                   // if does not exist then add
                   LocationActivity.mSavedLocations.add(mMainLocations.get(position));
                   holder.imageView.setImageResource(R.drawable.ic_remove);
               }
               EXIST_POSITION = LocationActivity.mSavedLocations.size();
               //Log.d(TAG, "onBindViewHolder: saved size is "+EXIST_POSITION);
           }
           catch (ArrayIndexOutOfBoundsException e){

           }
        });
    }
    private boolean exist(List<Location>mSavedLocations, Location location){
        for (int i = 0; i < LocationActivity.mSavedLocations.size(); i++) {
            if(mSavedLocations.get(i).city.equals(location.city)){
                EXIST_POSITION = i;
                //Log.d(TAG, "exist: "+mSavedLocations.get(i).city+" = "+location.city);
                return true;
            }
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return mLocations.size();
    }

    public Filter getFilter(){
        return userFilter;
    }

    private Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence ch) {
            List<Location> filterPost = new ArrayList<>();
            final String filterPattern = ch.toString().toLowerCase();
            if(ch==null || ch.length()==0){
                filterPost.addAll(mMainLocations);
            }
            else {
                for(Location m : mMainLocations){
                    String city = String.valueOf(m.city).toLowerCase();
                    String country = String.valueOf(m.country).toLowerCase();
                    if(city.contains(filterPattern) || country.contains(filterPattern) ){
                        filterPost.add(m);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filterPost;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mLocations.clear();
            mLocations.addAll((List<Location>)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private TextView textView;
        private ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView= itemView.findViewById(R.id.singleLocationTextId);
            imageView= itemView.findViewById(R.id.singleLocationImageId);
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


