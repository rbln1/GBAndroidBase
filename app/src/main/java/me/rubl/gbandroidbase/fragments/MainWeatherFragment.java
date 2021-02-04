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

public class MainWeatherFragment extends Fragment {

    FrameLayout additionalInfoContainer;

    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, fragment)
                .commit();

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            additionalInfoContainer = view.findViewById(R.id.additionalInfoContainer);
            HourlyWeatherFragment hourlyWeatherFragment = new HourlyWeatherFragment();
            fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.additionalInfoContainer, hourlyWeatherFragment)
                    .commit();
        }
    }
}
