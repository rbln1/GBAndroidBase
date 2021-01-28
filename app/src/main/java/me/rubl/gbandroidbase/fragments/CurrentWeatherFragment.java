package me.rubl.gbandroidbase.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.activities.CitiesActivity;
import me.rubl.gbandroidbase.activities.DayDetailsActivity;
import me.rubl.gbandroidbase.adapters.WeatherForWeekAdapter;
import me.rubl.gbandroidbase.entities.City;
import me.rubl.gbandroidbase.events.BusProvider;
import me.rubl.gbandroidbase.events.CityChangedEvent;
import me.rubl.gbandroidbase.events.ShowAddWeatherInfoEvent;

import static me.rubl.gbandroidbase.fragments.DayDetailsFragment.CITY_FOR_DAY_DETAILS_KEY;

public class CurrentWeatherFragment extends Fragment {

    private static final int CHANGE_CITY_REQUEST_CODE = 112;

    City currentCity = City.getMockCity();
    WeatherForWeekAdapter weekAdapter;
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMM");

    MaterialButton btnCityName;
    ImageButton btnOpenInYandex;
    MaterialButton btnShowDetails;
    TextView tvTemperatureLabel;
    TextView tvDateOfWeatherLabel;
    ImageView ivMainWeatherIcon;
    TextView tvWeatherTypeLabel;
    RecyclerView rvWeatherForWeek;
    LinearLayout llAdditionalWeatherInfo;
    TextView tvAddInfoWindValue;
    TextView tvAddInfoHumidityValue;
    TextView tvAddInfoPressureValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        btnCityName.setText(currentCity.getNameResourceId());

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            tvDateOfWeatherLabel.setText(sdf.format(currentCity.getWeatherCurrent().getDate()));
        }

        tvTemperatureLabel.setText(
                String.format("%d°", currentCity.getWeatherCurrent().getTemperatureInC()));

        switch (currentCity.getWeatherCurrent().getType()) {
            case CLOUDY:
                ivMainWeatherIcon.setImageResource(R.drawable.ic_cloudy);
                tvWeatherTypeLabel.setText(R.string.weather_type_cloudy);
                break;
            case SUNNY:
                ivMainWeatherIcon.setImageResource(R.drawable.ic_sunny);
                tvWeatherTypeLabel.setText(R.string.weather_type_sunny);
                break;
            case RAINY:
                ivMainWeatherIcon.setImageResource(R.drawable.ic_rainy);
                tvWeatherTypeLabel.setText(R.string.weather_type_rainy);
                break;
        }

        tvAddInfoWindValue.setText(String.format(getString(R.string.add_info_wind_value_format),
                currentCity.getWeatherCurrent().getWildSpeed()));
        tvAddInfoHumidityValue.setText(
                String.format(getString(R.string.add_info_humidity_value_format),
                        currentCity.getWeatherCurrent().getHumidity()));
        tvAddInfoPressureValue.setText(
                String.format(getString(R.string.add_info_pressure_value_format),
                        currentCity.getWeatherCurrent().getPressure()));

        btnCityName.setOnClickListener((v) -> {
            getActivity().startActivity(new Intent(getActivity(), CitiesActivity.class));
        });
        btnOpenInYandex.setOnClickListener((v) -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentCity.getWeatherURL())));
        });
        if (btnShowDetails != null) {
            btnShowDetails.setOnClickListener((v) -> {
                startActivity(new Intent(getActivity(), DayDetailsActivity.class)
                        .putExtra(CITY_FOR_DAY_DETAILS_KEY, currentCity));
            });
        }

        weekAdapter = new WeatherForWeekAdapter(currentCity.getWeatherOnWeek());
        LinearLayoutManager llManager =
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rvWeatherForWeek.setLayoutManager(llManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                rvWeatherForWeek.getContext(),
                llManager.getOrientation()
        );
        rvWeatherForWeek.addItemDecoration(dividerItemDecoration);
        rvWeatherForWeek.setAdapter(weekAdapter);
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

    private void findViews(View view) {
        btnCityName = view.findViewById(R.id.btnCityName);
        btnOpenInYandex = view.findViewById(R.id.btnOpenInYandex);
        tvDateOfWeatherLabel = view.findViewById(R.id.tvDateOfWeatherLabel);
        tvTemperatureLabel = view.findViewById(R.id.tvMainTemperatureLabel);
        ivMainWeatherIcon = view.findViewById(R.id.ivMainWeatherIcon);
        tvWeatherTypeLabel = view.findViewById(R.id.tvWeatherTypeLabel);
        rvWeatherForWeek = view.findViewById(R.id.rvWeatherForWeek);
        llAdditionalWeatherInfo = view.findViewById(R.id.llAdditionalWeatherInfo);
        tvAddInfoWindValue = view.findViewById(R.id.tvAddInfoWindValue);
        tvAddInfoHumidityValue = view.findViewById(R.id.tvAddInfoHumidityValue);
        tvAddInfoPressureValue = view.findViewById(R.id.tvAddInfoPressureValue);

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            btnShowDetails = view.findViewById(R.id.btnShowDayDetails);
        }
    }

    @Subscribe public void onAddInfoShowingStateChanged(ShowAddWeatherInfoEvent event) {
        llAdditionalWeatherInfo.setVisibility(
                event.isShowAddWeatherInfo() ? View.VISIBLE : View.INVISIBLE);
    }

    @Subscribe public void onCityChanged(CityChangedEvent event) {

        currentCity = event.getNewCity();

        btnCityName.setText(currentCity.getNameResourceId());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            tvDateOfWeatherLabel.setText(sdf.format(currentCity.getWeatherCurrent().getDate()));
        }
        tvTemperatureLabel.setText(String.format("%d°", currentCity.getAverageTemperatureInC()));

        switch (currentCity.getWeatherCurrent().getType()) {
            case CLOUDY:
                ivMainWeatherIcon.setImageResource(R.drawable.ic_cloudy);
                tvWeatherTypeLabel.setText(R.string.weather_type_cloudy);
                break;
            case SUNNY:
                ivMainWeatherIcon.setImageResource(R.drawable.ic_sunny);
                tvWeatherTypeLabel.setText(R.string.weather_type_sunny);
                break;
            case RAINY:
                ivMainWeatherIcon.setImageResource(R.drawable.ic_rainy);
                tvWeatherTypeLabel.setText(R.string.weather_type_rainy);
                break;
        }

        tvAddInfoWindValue.setText(String.format(getString(R.string.add_info_wind_value_format),
                currentCity.getWeatherCurrent().getWildSpeed()));
        tvAddInfoHumidityValue.setText(
                String.format(getString(R.string.add_info_humidity_value_format),
                        currentCity.getWeatherCurrent().getHumidity()));
        tvAddInfoPressureValue.setText(
                String.format(getString(R.string.add_info_pressure_value_format),
                        currentCity.getWeatherCurrent().getPressure()));

        if (weekAdapter != null) {
            weekAdapter.update(currentCity.getWeatherOnWeek());
        }
    }
}
