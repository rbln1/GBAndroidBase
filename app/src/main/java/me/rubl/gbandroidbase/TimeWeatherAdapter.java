package me.rubl.gbandroidbase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeWeatherAdapter extends RecyclerView.Adapter<TimeWeatherAdapter.ViewHolder>{

    public static final int TEMPORARY_ITEM_COUNT = 15;

    public TimeWeatherAdapter() {
    }

    @NonNull
    @Override
    public TimeWeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_weather, parent, false);

        return new TimeWeatherAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeWeatherAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return TEMPORARY_ITEM_COUNT;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        //TODO bind views

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}