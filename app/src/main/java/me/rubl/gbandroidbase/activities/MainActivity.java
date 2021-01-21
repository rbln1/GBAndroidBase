package me.rubl.gbandroidbase.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.entities.City;
import me.rubl.gbandroidbase.fragments.DayDetailsFragment;
import me.rubl.gbandroidbase.fragments.MainFragment;

import static me.rubl.gbandroidbase.fragments.DayDetailsFragment.CITY_FOR_DAY_DETAILS_KEY;

public class MainActivity extends AppCompatActivity {

    FrameLayout mainContainer;
    FrameLayout additionalInfoContainer;

    FragmentManager fragmentManager;

    City currentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentCity = City.getMockCity();

        mainContainer = findViewById(R.id.mainContainer);

        MainFragment fragment = new MainFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, fragment)
                .commit();

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            additionalInfoContainer = findViewById(R.id.additionalInfoContainer);

            DayDetailsFragment detailsFragment = new DayDetailsFragment();
            Bundle args = new Bundle();
            args.putParcelable(CITY_FOR_DAY_DETAILS_KEY, currentCity);
            detailsFragment.setArguments(args);

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.additionalInfoContainer, detailsFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(City.ARGUMENT_KEY, currentCity);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        City tmpCity = savedInstanceState.getParcelable(City.ARGUMENT_KEY);
        if (tmpCity != null) currentCity = tmpCity;
    }
}