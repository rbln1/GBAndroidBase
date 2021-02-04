package me.rubl.gbandroidbase.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.squareup.otto.Subscribe;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.core.Settings;
import me.rubl.gbandroidbase.events.BusProvider;
import me.rubl.gbandroidbase.events.ChangeThemeEvent;
import me.rubl.gbandroidbase.fragments.HourlyWeatherFragment;
import me.rubl.gbandroidbase.model.core.City;

import static me.rubl.gbandroidbase.fragments.HourlyWeatherFragment.CITY_FOR_DAY_DETAILS_KEY;

public class HourlyWeatherActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout dayDetailsContainer;

    City currentCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Settings.getInstance().isDarkTheme()) {
            setTheme(R.style.Theme_GBAndroidBase_Dark);
        } else {
            setTheme(R.style.Theme_GBAndroidBase);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_details);

        BusProvider.getInstance().register(this);

        findViews();

        currentCity = getIntent().getParcelableExtra(CITY_FOR_DAY_DETAILS_KEY);
        if (currentCity == null) {
            currentCity = Settings.getInstance().getCurrentCity();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        } else {
            if (currentCity != null) {
                toolbar.setTitle(currentCity.getName());
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

                HourlyWeatherFragment hourlyWeatherFragment = new HourlyWeatherFragment();
                Bundle args = new Bundle();
                args.putParcelable(CITY_FOR_DAY_DETAILS_KEY, currentCity);
                hourlyWeatherFragment.setArguments(args);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.dayDetailsContainer, hourlyWeatherFragment)
                        .commit();
            } else {
                finish();
            }
        }
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        dayDetailsContainer = findViewById(R.id.dayDetailsContainer);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Subscribe
    public void onThemeChanged(ChangeThemeEvent event) {
        recreate();
    }

    @Override
    protected void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }
}
