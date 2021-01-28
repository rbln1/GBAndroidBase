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

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.adapters.CitiesAdapter;
import me.rubl.gbandroidbase.core.Utils;
import me.rubl.gbandroidbase.entities.City;
import me.rubl.gbandroidbase.events.BusProvider;
import me.rubl.gbandroidbase.events.CityChangedEvent;
import me.rubl.gbandroidbase.listeners.OnItemClickCityRecyclerListener;

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

        citiesList = City.getMockCities();
        citiesAdapter = new CitiesAdapter(getActivity(), citiesList, this);

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
            String cityName = this.getResources().getString(city.getNameResourceId());
            if (cityName.toLowerCase().contains(text.toLowerCase())) {
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
                getString(city.getNameResourceId())), Snackbar.LENGTH_SHORT)
                .setAction(R.string.ok, (v) -> {
                    currentCity = city;
                    BusProvider.getInstance().post(new CityChangedEvent(currentCity));
                }).show();
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
