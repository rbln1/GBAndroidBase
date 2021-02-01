package me.rubl.gbandroidbase.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.activities.CitiesActivity;
import me.rubl.gbandroidbase.activities.HourlyWeatherActivity;
import me.rubl.gbandroidbase.adapters.DailyWeatherAdapter;
import me.rubl.gbandroidbase.core.Settings;
import me.rubl.gbandroidbase.enums.UIMode;
import me.rubl.gbandroidbase.events.BusProvider;
import me.rubl.gbandroidbase.events.CityChangedEvent;
import me.rubl.gbandroidbase.events.ShowAddWeatherInfoEvent;
import me.rubl.gbandroidbase.model.core.City;
import me.rubl.gbandroidbase.model.owm.onecall.daily.DailyWeatherResponse;
import me.rubl.gbandroidbase.model.owm.weather.WeatherResponse;
import me.rubl.gbandroidbase.repositories.OwmWeatherRepositoryImpl;

import static me.rubl.gbandroidbase.fragments.HourlyWeatherFragment.CITY_FOR_DAY_DETAILS_KEY;

public class CurrentWeatherFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String YANDEX_URL_FORMAT = "https://yandex.ru/pogoda/%s/details?via=ms";

    City currentCity;
    DailyWeatherAdapter weekAdapter;
    WeatherResponse currentWeather;
    DailyWeatherResponse dailyWeather;
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMM");

    ConstraintLayout clCurrentWeatherContainer;
    TextView tvCurrentWeatherNotFound;
    ProgressBar pbCurrentWeatherLoader;
    MaterialButton btnCityName;
    MaterialButton btnShowDetails;
    ImageButton btnOpenInYandex;
    ImageView ivMainWeatherIcon;
    TextView tvTemperatureLabel;
    TextView tvDateOfWeatherLabel;
    TextView tvWeatherTypeLabel;
    TextView tvAddInfoWindValue;
    TextView tvAddInfoHumidityValue;
    TextView tvAddInfoPressureValue;
    RecyclerView rvWeatherForWeek;
    LinearLayout llAdditionalWeatherInfo;
    SwipeRefreshLayout srlCurrentWeatherContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        currentCity = Settings.getInstance().getCurrentCity();

        onRefresh();
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

    private void initViews(View view) {
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
        srlCurrentWeatherContainer = view.findViewById(R.id.srlCurrentWeatherContainer);
        clCurrentWeatherContainer = view.findViewById(R.id.clCurrentWeatherContainer);
        tvCurrentWeatherNotFound = view.findViewById(R.id.tvCurrentWeatherNotFound);
        pbCurrentWeatherLoader = view.findViewById(R.id.pbCurrentWeatherLoader);

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            btnShowDetails = view.findViewById(R.id.btnShowDayDetails);
        }

        btnCityName.setOnClickListener((v) -> {
            getActivity().startActivity(new Intent(getActivity(), CitiesActivity.class));
        });
        btnOpenInYandex.setOnClickListener((v) -> {
            String name = currentCity.getName().trim().replace(" ", "%20");
            startActivity(new Intent(
                    Intent.ACTION_VIEW, Uri.parse(String.format(YANDEX_URL_FORMAT, name))));
        });
        if (btnShowDetails != null) {
            btnShowDetails.setOnClickListener((v) -> {
                startActivity(new Intent(getActivity(), HourlyWeatherActivity.class)
                        .putExtra(CITY_FOR_DAY_DETAILS_KEY, currentCity));
            });
        }

        srlCurrentWeatherContainer.setOnRefreshListener(this);
        srlCurrentWeatherContainer.setColorSchemeResources(
                R.color.primaryColor, R.color.primaryDarkColor,
                R.color.secondaryColor, R.color.secondaryDarkColor);
    }

    @Subscribe public void onAddInfoShowingStateChanged(ShowAddWeatherInfoEvent event) {
        llAdditionalWeatherInfo.setVisibility(
                event.isShowAddWeatherInfo() ? View.VISIBLE : View.INVISIBLE);
    }

    @Subscribe public void onCityChanged(CityChangedEvent event) {
        currentCity = Settings.getInstance().getCurrentCity();
        onRefresh();
    }

    private void setUIMode(UIMode mode) {
        switch (mode) {
            case LOADING:
                clCurrentWeatherContainer.setVisibility(View.GONE);
                tvCurrentWeatherNotFound.setVisibility(View.GONE);
                pbCurrentWeatherLoader.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                clCurrentWeatherContainer.setVisibility(View.GONE);
                pbCurrentWeatherLoader.setVisibility(View.GONE);
                tvCurrentWeatherNotFound.setVisibility(View.VISIBLE);
                break;
            case SUCCESSFULLY:
                tvCurrentWeatherNotFound.setVisibility(View.GONE);
                pbCurrentWeatherLoader.setVisibility(View.GONE);
                clCurrentWeatherContainer.setVisibility(View.VISIBLE);
                break;
            default: break;
        }
    }

    private void fillViews() {
        if(isAdded()) {
            btnCityName.setText(currentWeather.getName());

            tvTemperatureLabel.setText(String.format(
                    getString(R.string.weather_temp_value_format), currentWeather.getMain().getTemp()));

            tvAddInfoWindValue.setText(String.format(getString(R.string.add_info_wind_value_format),
                    currentWeather.getWind().getSpeed()));
            tvAddInfoHumidityValue.setText(
                    String.format(getString(R.string.add_info_humidity_value_format),
                            currentWeather.getMain().getHumidity()));
            tvAddInfoPressureValue.setText(
                    String.format(getString(R.string.add_info_pressure_value_format),
                            currentWeather.getMain().getPressure()));

//        switch (currentCity.getWeatherCurrent().getType()) {
//            case CLOUDY:
//                ivMainWeatherIcon.setImageResource(R.drawable.ic_cloudy);
//                tvWeatherTypeLabel.setText(R.string.weather_type_cloudy);
//                break;
//            case SUNNY:
//                ivMainWeatherIcon.setImageResource(R.drawable.ic_sunny);
//                tvWeatherTypeLabel.setText(R.string.weather_type_sunny);
//                break;
//            case RAINY:
//                ivMainWeatherIcon.setImageResource(R.drawable.ic_rainy);
//                tvWeatherTypeLabel.setText(R.string.weather_type_rainy);
//                break;
//        }

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                tvDateOfWeatherLabel.setText(sdf.format(new Date(currentWeather.getDt() * 1000)));
            }

            weekAdapter = new DailyWeatherAdapter(dailyWeather.getDaily());
            LinearLayoutManager llManager =
                    new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
            rvWeatherForWeek.setLayoutManager(llManager);
            rvWeatherForWeek.setAdapter(weekAdapter);
        }
    }

    @Override
    public void onRefresh() {
        setUIMode(UIMode.LOADING);
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            currentWeather =
                    new OwmWeatherRepositoryImpl().getWeatherByCityName(currentCity.getName());
            if (currentWeather != null) {
                dailyWeather = new OwmWeatherRepositoryImpl()
                        .getDailyWeatherByLatLon(
                                currentWeather.getCoord().getLat(),
                                currentWeather.getCoord().getLon());
            }
            handler.post(() -> {
                if (currentWeather == null || dailyWeather == null) setUIMode(UIMode.ERROR);
                else {
                    setUIMode(UIMode.SUCCESSFULLY);
                    currentCity = new City(currentWeather.getName(),
                            currentWeather.getCoord().getLat(), currentWeather.getCoord().getLon());
                    BusProvider.getInstance().post(currentCity);
                    fillViews();
                }
                srlCurrentWeatherContainer.setRefreshing(false);
            });
        }).start();
    }
}
