package me.rubl.gbandroidbase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.model.owm.onecall.hourly.Hourly;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder>{

    private Hourly[] hourlyWeather;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM");

    public HourlyWeatherAdapter(Hourly[] hourlyWeather) {
        this.hourlyWeather = hourlyWeather;
    }

    @NonNull
    @Override
    public HourlyWeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hourly_weather, parent, false);

        return new HourlyWeatherAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherAdapter.ViewHolder holder, int position) {
        Hourly weather = hourlyWeather[position];
        holder.tvItemTime.setText(sdf.format(new Date((long) weather.getDt() * 1000)));
        holder.tvItemTemperature.setText(String.format("%.1f°", weather.getTemp()));
    }

    @Override
    public int getItemCount() {
        return hourlyWeather.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemTime;
        TextView tvItemTemperature;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemTime = itemView.findViewById(R.id.tvItemTime);
            tvItemTemperature = itemView.findViewById(R.id.tvItemTemperature);
        }
    }

    public void update(Hourly[] newWeatherForDay) {
        this.hourlyWeather = newWeatherForDay;
        notifyDataSetChanged();
    }
}
