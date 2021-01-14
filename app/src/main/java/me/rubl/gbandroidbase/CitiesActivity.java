package me.rubl.gbandroidbase;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.rubl.gbandroidbase.entity.City;

public class CitiesActivity extends AppCompatActivity implements OnItemClickCityRecyclerListener {

    Toolbar toolbar;
    RecyclerView rvCities;
    EditText etSearchCityName;

    CitiesAdapter citiesAdapter;
    ArrayList<City> citiesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        findViews();

        toolbar.setTitle(R.string.settlement_selection);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        citiesList = City.getMockCities();
        citiesAdapter = new CitiesAdapter(this, citiesList, this);

        LinearLayoutManager llManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvCities.setLayoutManager(llManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                rvCities.getContext(),
                llManager.getOrientation()
        );
        rvCities.addItemDecoration(dividerItemDecoration);
        rvCities.setAdapter(citiesAdapter);

        etSearchCityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<City> filteredList = new ArrayList<>();

        for (City city : citiesList) {
            String cityName = this.getResources().getString(city.getNameResourceId());
            if (cityName.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(city);
            }
        }

        citiesAdapter.filterList(filteredList);
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        rvCities = findViewById(R.id.rvCities);
        etSearchCityName = findViewById(R.id.etCityName);
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

    @Override
    public void onItemClick(City city) {
        Intent result = new Intent();
        result.putExtra(City.ARGUMENT_KEY, city);
        setResult(RESULT_OK, result);
        finish();
    }
}
