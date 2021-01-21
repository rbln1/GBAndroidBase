package me.rubl.gbandroidbase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.entities.Weather;

public class WeatherForDayAdapter extends RecyclerView.Adapter<WeatherForDayAdapter.ViewHolder>{

    private Weather[] weatherForDay;

    public WeatherForDayAdapter(Weather[] weatherForDay) {
        this.weatherForDay = weatherForDay;
    }

    @NonNull
    @Override
    public WeatherForDayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day_details_weather, parent, false);

        return new WeatherForDayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherForDayAdapter.ViewHolder holder, int position) {
        Weather weather = weatherForDay[position];

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

        holder.tvItemTime.setText(String.format("%d:00", position));

        holder.tvItemTemperature.setText(String.format("%d°", weather.getTemperatureInC()));
    }

    @Override
    public int getItemCount() {
        return weatherForDay.length;
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
        this.weatherForDay = newWeatherForDay;
        notifyDataSetChanged();
    }
}