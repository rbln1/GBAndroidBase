package me.rubl.gbandroidbase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Button btnCityName;
    ImageButton btnSettings;
    RecyclerView rvTimeWeather;

    TimeWeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        btnCityName.setOnClickListener((v) -> startActivity(new Intent(this, CitiesActivity.class)));
        btnSettings.setOnClickListener((v) -> startActivity(new Intent(this, SettingsActivity.class)));

        adapter = new TimeWeatherAdapter();

        LinearLayoutManager llManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvTimeWeather.setLayoutManager(llManager);
        rvTimeWeather.setAdapter(adapter);
    }

    private void findViews() {
        btnCityName = findViewById(R.id.btnCityName);
        btnSettings = findViewById(R.id.btnSettings);
        rvTimeWeather = findViewById(R.id.rvTimeWeather);
    }
}