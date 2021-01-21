package me.rubl.gbandroidbase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.adapters.WeatherForDayAdapter;
import me.rubl.gbandroidbase.entities.City;
import me.rubl.gbandroidbase.entities.Weather;
import me.rubl.gbandroidbase.events.BusProvider;
import me.rubl.gbandroidbase.events.CityChangedEvent;

public class DayDetailsFragment extends Fragment {

    public static final String CITY_FOR_DAY_DETAILS_KEY = "CityForDayDetailsKey";

    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMM");
    Date dateOfDay;
    Weather[] weatherForDay;
    WeatherForDayAdapter adapter;

    TextView tvSelectedDay;
    RecyclerView rvWeatherInHours;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        Bundle args = getArguments();

        if (args != null) {
            City city = args.getParcelable(CITY_FOR_DAY_DETAILS_KEY);
            dateOfDay = city.getWeatherCurrent().getDate();
            weatherForDay = city.getWeatherOnDay();

            tvSelectedDay.setText(sdf.format(dateOfDay));

            adapter = new WeatherForDayAdapter(weatherForDay);

            rvWeatherInHours.setLayoutManager(
                        new LinearLayoutManager(getContext(),
                                RecyclerView.VERTICAL, false));

            rvWeatherInHours.setAdapter(adapter);
        }
    }

    private void findViews(View view) {
        tvSelectedDay = view.findViewById(R.id.tvSelectedDay);
        rvWeatherInHours = view.findViewById(R.id.rvWeatherInHours);
    }

    @Override
    public void onResume() {
        super.onResume();

        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        BusProvider.getInstance().unregister(this);
    }

    @Subscribe public void onCityChanged(CityChangedEvent event) {
        tvSelectedDay.setText(sdf.format(event.getNewCity().getWeatherCurrent().getDate()));
        adapter.update(event.getNewCity().getWeatherOnDay());
    }
}
