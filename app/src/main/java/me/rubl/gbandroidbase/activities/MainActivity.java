package me.rubl.gbandroidbase.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.otto.Subscribe;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.core.Settings;
import me.rubl.gbandroidbase.events.BusProvider;
import me.rubl.gbandroidbase.events.ChangeThemeEvent;
import me.rubl.gbandroidbase.events.CityChangedEvent;
import me.rubl.gbandroidbase.model.core.City;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView navHeaderTitle;
    TextView navHeaderSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Settings.getInstance().isDarkTheme()) {
            setTheme(R.style.Theme_GBAndroidBase_Dark);
        } else {
            setTheme(R.style.Theme_GBAndroidBase);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BusProvider.getInstance().register(this);

        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.menu_weather_page, R.id.menu_about_page, R.id.menu_settings_page)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);

        navHeaderTitle = headerView.findViewById(R.id.navHeaderTitle);
        navHeaderSubtitle = headerView.findViewById(R.id.navHeaderSubtitle);

        City currentCity = Settings.getInstance().getCurrentCity();
        navHeaderTitle.setText(currentCity.getName());
        navHeaderSubtitle.setText(String.format("%s %s", currentCity.getLat(), currentCity.getLon()));
    }

    @Subscribe public void onThemeChanged(ChangeThemeEvent event) {
        recreate();
    }

    @Subscribe public void onCityChanged(CityChangedEvent event) {
        City currentCity = Settings.getInstance().getCurrentCity();
        navHeaderTitle.setText(currentCity.getName());
        navHeaderSubtitle.setText(String.format("%s, %s", currentCity.getLat(), currentCity.getLon()));
    }

    @Override
    protected void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.exit))
                .setMessage(getString(R.string.log_out_question))
                .setCancelable(true)
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton(getString(R.string.yes), ((dialog, which) -> {
                    super.onBackPressed();
                }));
        builder.create().show();
    }
}