package me.rubl.gbandroidbase.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.otto.Subscribe;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.adapters.HourlyWeatherAdapter;
import me.rubl.gbandroidbase.core.Settings;
import me.rubl.gbandroidbase.enums.UIMode;
import me.rubl.gbandroidbase.events.BusProvider;
import me.rubl.gbandroidbase.events.CityChangedEvent;
import me.rubl.gbandroidbase.model.core.City;
import me.rubl.gbandroidbase.model.owm.onecall.hourly.Hourly;
import me.rubl.gbandroidbase.repositories.OwmWeatherRepositoryImpl;

public class HourlyWeatherFragment extends Fragment {

    public static final String CITY_FOR_DAY_DETAILS_KEY = "CityForDayDetailsKey";

    Hourly[] hourlyWeather;
    HourlyWeatherAdapter adapter;
    City currentCity;

    ProgressBar pbHourlyWeatherLoader;
    TextView tvHourlyWeatherNotFound;
    RecyclerView rvWeatherInHours;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hourly_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        Bundle args = getArguments();
        if (args != null) {
            currentCity = args.getParcelable(CITY_FOR_DAY_DETAILS_KEY);
        } else {
            currentCity = Settings.getInstance().getCurrentCity();
        }

        fillViews();
    }

    private void findViews(View view) {
        pbHourlyWeatherLoader = view.findViewById(R.id.pbHourlyWeatherLoader);
        tvHourlyWeatherNotFound = view.findViewById(R.id.tvHourlyWeatherNotFound);
        rvWeatherInHours = view.findViewById(R.id.rvWeatherInHours);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    private void fillViews() {
        setUIMode(UIMode.LOADING);
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            hourlyWeather = new OwmWeatherRepositoryImpl()
                    .getHourlyWeatherByLayLon(currentCity.getLat(), currentCity.getLon()).getHourly();
            handler.post(() -> {
                if (hourlyWeather == null) {
                    setUIMode(UIMode.ERROR);
                } else if (adapter != null) {
                    adapter.update(hourlyWeather);
                    setUIMode(UIMode.SUCCESSFULLY);
                } else {
                    adapter = new HourlyWeatherAdapter(hourlyWeather);
                    rvWeatherInHours.setLayoutManager(
                            new LinearLayoutManager(getContext(),
                                    RecyclerView.VERTICAL, false));
                    rvWeatherInHours.setAdapter(adapter);
                    setUIMode(UIMode.SUCCESSFULLY);
                }

            });
        }).start();
    }

    @Subscribe public void onCityChanged(CityChangedEvent event) {
        currentCity = Settings.getInstance().getCurrentCity();
        fillViews();
    }

    private void setUIMode(UIMode mode) {
        switch (mode) {
            case LOADING:
                rvWeatherInHours.setVisibility(View.GONE);
                tvHourlyWeatherNotFound.setVisibility(View.GONE);
                pbHourlyWeatherLoader.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                rvWeatherInHours.setVisibility(View.GONE);
                pbHourlyWeatherLoader.setVisibility(View.GONE);
                tvHourlyWeatherNotFound.setVisibility(View.VISIBLE);
                break;
            case SUCCESSFULLY:
                tvHourlyWeatherNotFound.setVisibility(View.GONE);
                pbHourlyWeatherLoader.setVisibility(View.GONE);
                rvWeatherInHours.setVisibility(View.VISIBLE);
                break;
            default: break;
        }
    }
}
