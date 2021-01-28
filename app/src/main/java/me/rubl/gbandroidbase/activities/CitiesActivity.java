package me.rubl.gbandroidbase.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.otto.Subscribe;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.core.Settings;
import me.rubl.gbandroidbase.events.BusProvider;
import me.rubl.gbandroidbase.events.ChangeThemeEvent;
import me.rubl.gbandroidbase.events.CityChangedEvent;

public class CitiesActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Settings.getInstance().isDarkTheme()) {
            setTheme(R.style.Theme_GBAndroidBase_Dark);
        } else {
            setTheme(R.style.Theme_GBAndroidBase);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        findViews();

        toolbar.setTitle(R.string.settlement_selection);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
    }

    @Subscribe public void onCityChanged(CityChangedEvent event) {
        finish();
    }

    @Subscribe public void onThemeChanged(ChangeThemeEvent event) {
        recreate();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
}
