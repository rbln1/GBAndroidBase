package me.rubl.gbandroidbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import me.rubl.gbandroidbase.entity.City;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "GBAndroidBase.Logs";
    private static final int CHANGE_CITY_REQUEST_CODE = 112;

    Button btnCityName;
    Button btnOpenInYandex;
    ImageButton btnSettings;
    RecyclerView rvTimeWeather;
    TextView tvTemperatureLabel;

    TimeWeatherAdapter adapter;
    City mainCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainCity = City.getMockCity();

        findViews();

        btnCityName.setText(mainCity.getNameResourceId());
        tvTemperatureLabel.setText(String.format("%d°", mainCity.getTemperatureInC()));

        btnCityName.setOnClickListener((v) -> startActivityForResult(new Intent(this, CitiesActivity.class), CHANGE_CITY_REQUEST_CODE));
        btnSettings.setOnClickListener((v) -> startActivity(new Intent(this, SettingsActivity.class)));
        btnOpenInYandex.setOnClickListener((v) -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mainCity.getWeatherURL()))));

        adapter = new TimeWeatherAdapter();

        LinearLayoutManager llManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvTimeWeather.setLayoutManager(llManager);
        rvTimeWeather.setAdapter(adapter);
    }

    private void findViews() {
        btnCityName = findViewById(R.id.btnCityName);
        btnOpenInYandex = findViewById(R.id.btnOpenInYandex);
        btnSettings = findViewById(R.id.btnSettings);
        rvTimeWeather = findViewById(R.id.rvTimeWeather);
        tvTemperatureLabel = findViewById(R.id.tvMainTemperatureLabel);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnCityName.setText(mainCity.getNameResourceId());
        tvTemperatureLabel.setText(String.format("%d°", mainCity.getTemperatureInC()));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(City.ARGUMENT_KEY, mainCity);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        City tmpCity = savedInstanceState.getParcelable(City.ARGUMENT_KEY);
        if (tmpCity != null) mainCity = tmpCity;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != CHANGE_CITY_REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (resultCode == RESULT_OK){
            mainCity = data.getParcelableExtra(City.ARGUMENT_KEY);
        }
    }

}