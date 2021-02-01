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
import me.rubl.gbandroidbase.model.owm.onecall.daily.Daily;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder>{

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
    private Daily[] weatherForWeek;

    public DailyWeatherAdapter(Daily[] weatherForDay) {
        this.weatherForWeek = weatherForDay;
    }

    @NonNull
    @Override
    public DailyWeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_weather, parent, false);

        return new DailyWeatherAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherAdapter.ViewHolder holder, int position) {
        Daily weather = weatherForWeek[position];

        holder.tvItemTime.setText(sdf.format(new Date((long) weather.getDt() * 1000)));
        holder.tvItemTemperature.setText(String.format("%.1f°", weather.getTemp().getDay()));
    }

    @Override
    public int getItemCount() {
        return weatherForWeek.length;
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

    public void update(Daily[] newWeatherForWeek) {
        this.weatherForWeek = newWeatherForWeek;
        notifyDataSetChanged();
    }
}