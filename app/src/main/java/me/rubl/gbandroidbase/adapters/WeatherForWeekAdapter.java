package me.rubl.gbandroidbase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.entities.Weather;

public class WeatherForWeekAdapter extends RecyclerView.Adapter<WeatherForWeekAdapter.ViewHolder>{

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");

    private Weather[] weatherForWeek;

    public WeatherForWeekAdapter(Weather[] weatherForDay) {
        this.weatherForWeek = weatherForDay;
    }

    @NonNull
    @Override
    public WeatherForWeekAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_week_weather, parent, false);

        return new WeatherForWeekAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherForWeekAdapter.ViewHolder holder, int position) {
        Weather weather = weatherForWeek[position];

        switch (weather.getType()) {
            case CLOUDY:
                holder.ivItemTimeWeatherIcon.setImageResource(R.drawable.ic_cloudy);
                break;
            case SUNNY:
                holder.ivItemTimeWeatherIcon.setImageResource(R.drawable.ic_sunny);
                break;
            case RAINY:
                holder.ivItemTimeWeatherIcon.setImageResource(R.drawable.ic_rainy);
                break;
        }

        holder.tvItemTime.setText(sdf.format(weather.getDate()));
        holder.tvItemTemperature.setText(String.format("%d°", weather.getTemperatureInC()));
    }

    @Override
    public int getItemCount() {
        return weatherForWeek.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivItemTimeWeatherIcon;
        TextView tvItemTime;
        TextView tvItemTemperature;

        public ViewHolder(View itemView) {
            super(itemView);

            ivItemTimeWeatherIcon = itemView.findViewById(R.id.ivItemTimeWeatherIcon);
            tvItemTime = itemView.findViewById(R.id.tvItemTime);
            tvItemTemperature = itemView.findViewById(R.id.tvItemTemperature);
        }
    }

    public void update(Weather[] newWeatherForDay) {
        this.weatherForWeek = newWeatherForDay;
        notifyDataSetChanged();
    }
}