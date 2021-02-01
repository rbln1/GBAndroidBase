package me.rubl.gbandroidbase.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.adapters.CitiesAdapter;
import me.rubl.gbandroidbase.core.Settings;
import me.rubl.gbandroidbase.core.Utils;
import me.rubl.gbandroidbase.events.BusProvider;
import me.rubl.gbandroidbase.events.CityChangedEvent;
import me.rubl.gbandroidbase.listeners.OnItemClickCityRecyclerListener;
import me.rubl.gbandroidbase.model.core.City;

public class CitiesFragment extends Fragment implements OnItemClickCityRecyclerListener {

    RecyclerView rvCities;
    TextInputLayout tilSearchCity;
    TextInputEditText tietSearchCity;

    CitiesAdapter citiesAdapter;
    ArrayList<City> citiesList;
    City currentCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        citiesList = new ArrayList<>(
                Arrays.asList(
                        new City("Москва", 55.7522, 37.6156),
                        new City("Саратов", 51.5667, 46.0333),
                        new City("Лондон", 51.5085, -0.1257),
                        new City("Париж", 48.8534, 2.3488),
                        new City("Неизвестный", 0, 0),
                        new City("Новый Уренгой", 66.0833, 76.6333)
                ));
        citiesAdapter = new CitiesAdapter(citiesList, this);

        LinearLayoutManager llManager =
                new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvCities.setLayoutManager(llManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                rvCities.getContext(),
                llManager.getOrientation()
        );
        rvCities.addItemDecoration(dividerItemDecoration);
        rvCities.setAdapter(citiesAdapter);

        tietSearchCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

                if (!Utils.Strings.checkCityName(s.toString())) {
                    tietSearchCity.setError(getString(R.string.city_wrong_name));
                } else tietSearchCity.setError(null);
            }
        });
    }

    private void filter(String text) {
        ArrayList<City> filteredList = new ArrayList<>();

        for (City city : citiesList) {
            if (city.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(city);
            }
        }

        citiesAdapter.filterList(filteredList);
    }

    private void findViews(View root) {
        rvCities = root.findViewById(R.id.rvCities);
        tilSearchCity = root.findViewById(R.id.tilSearchCity);
        tietSearchCity = root.findViewById(R.id.tietSearchCity);
    }

    @Override
    public void onItemClick(City city) {
        Snackbar.make(rvCities, String.format(getString(R.string.city_change),
                city.getName()), Snackbar.LENGTH_SHORT)
                .setAction(R.string.ok, (v) -> {
                    currentCity = city;
                    Settings.getInstance().setCurrentCity(currentCity);
                    BusProvider.getInstance().post(new CityChangedEvent());
                }).show();
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
}
