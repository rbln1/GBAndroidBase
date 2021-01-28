package me.rubl.gbandroidbase.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.entities.City;

import static me.rubl.gbandroidbase.fragments.DayDetailsFragment.CITY_FOR_DAY_DETAILS_KEY;

public class MainWeatherFragment extends Fragment {

    FrameLayout currentWeatherContainer;
    FrameLayout additionalInfoContainer;

    FragmentManager fragmentManager;
    City currentCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentCity = City.getMockCity();

        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, fragment)
                .commit();
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            additionalInfoContainer = view.findViewById(R.id.additionalInfoContainer);
            DayDetailsFragment detailsFragment = new DayDetailsFragment();
            Bundle args = new Bundle();
            args.putParcelable(CITY_FOR_DAY_DETAILS_KEY, currentCity);
            detailsFragment.setArguments(args);
            fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.additionalInfoContainer, detailsFragment)
                    .commit();
        }
    }
}
