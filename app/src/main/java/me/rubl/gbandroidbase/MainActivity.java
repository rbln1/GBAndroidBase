package me.rubl.gbandroidbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "GBAndroidBase.Logs";

    Button btnCityName;
    ImageButton btnSettings;
    RecyclerView rvTimeWeather;

    TimeWeatherAdapter adapter;

    DataSaver dataSaver;
    long reloadCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onCreate()");

        dataSaver = DataSaver.getInstance();

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

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onResume()");

        Toast.makeText(this, "onResume() | reloadCounter: "
                + reloadCounter, Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onResume() | reloadCounter: " + reloadCounter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart()", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this, "onSaveInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onSaveInstanceState()");

        if (reloadCounter != 0) {
            dataSaver.setReloadCounter(reloadCounter);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "onRestoreInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onRestoreInstanceState()");

        long restoredReloadCounter = dataSaver.getReloadCounter();
        if (restoredReloadCounter != 0) reloadCounter = restoredReloadCounter;
        reloadCounter++;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "onBackPressed()", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onBackPressed()");
    }
}