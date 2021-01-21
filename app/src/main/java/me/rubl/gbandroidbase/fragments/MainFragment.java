package me.rubl.gbandroidbase.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import java.util.Arrays;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.activities.CitiesActivity;
import me.rubl.gbandroidbase.activities.DayDetailsActivity;
import me.rubl.gbandroidbase.activities.SettingsActivity;
import me.rubl.gbandroidbase.adapters.WeatherForWeekAdapter;
import me.rubl.gbandroidbase.entities.City;
import me.rubl.gbandroidbase.events.BusProvider;
import me.rubl.gbandroidbase.events.CityChangedEvent;

import static android.app.Activity.RESULT_OK;
import static me.rubl.gbandroidbase.fragments.DayDetailsFragment.CITY_FOR_DAY_DETAILS_KEY;

public class MainFragment extends Fragment {

    private static final int CHANGE_CITY_REQUEST_CODE = 112;

    City currentCity = City.getMockCity();
    WeatherForWeekAdapter weekAdapter;

    Button btnCityName;
    ImageButton btnSettings;
    ImageButton btnOpenInYandex;
    Button btnShowDetails;
    TextView tvTemperatureLabel;
    ImageView ivMainWeatherIcon;
    TextView tvWeatherTypeLabel;
    RecyclerView rvWeatherForWeek;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        btnCityName.setText(currentCity.getNameResourceId());
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

        btnCityName.setOnClickListener((v) -> {
            startActivityForResult(new Intent(getActivity(), CitiesActivity.class),
                    CHANGE_CITY_REQUEST_CODE);
        });
        btnSettings.setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        });
        btnOpenInYandex.setOnClickListener((v) -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentCity.getWeatherURL())));
        });
        if (btnShowDetails != null) {
            btnShowDetails.setOnClickListener((v) -> {
                System.out.println(Arrays.toString(currentCity.getWeatherOnDay()));
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
    public void onResume() {
        super.onResume();

        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        BusProvider.getInstance().unregister(this);
    }

    private void findViews(View view) {
        btnCityName = view.findViewById(R.id.btnCityName);
        btnOpenInYandex = view.findViewById(R.id.btnOpenInYandex);
        btnSettings = view.findViewById(R.id.btnSettings);
        tvTemperatureLabel = view.findViewById(R.id.tvMainTemperatureLabel);
        ivMainWeatherIcon = view.findViewById(R.id.ivMainWeatherIcon);
        tvWeatherTypeLabel = view.findViewById(R.id.tvWeatherTypeLabel);
        rvWeatherForWeek = view.findViewById(R.id.rvWeatherForWeek);

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            btnShowDetails = view.findViewById(R.id.btnShowDayDetails);
        }
    }

    @Produce public CityChangedEvent produceChangedCity() {
        return new CityChangedEvent(currentCity);
    }

    @Subscribe public void onCityChanged(CityChangedEvent event) {
        btnCityName.setText(event.getNewCity().getNameResourceId());
        tvTemperatureLabel.setText(String.format("%d°", event.getNewCity().getAverageTemperatureInC()));

        switch (event.getNewCity().getWeatherCurrent().getType()) {
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

        if (weekAdapter != null) {
            weekAdapter.update(event.getNewCity().getWeatherOnWeek());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != CHANGE_CITY_REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (resultCode == RESULT_OK){
            currentCity = data.getParcelableExtra(City.ARGUMENT_KEY);
            BusProvider.getInstance().post(produceChangedCity());
        }
    }
}
