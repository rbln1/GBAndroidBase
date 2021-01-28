package me.rubl.gbandroidbase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.checkbox.MaterialCheckBox;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.core.Settings;
import me.rubl.gbandroidbase.events.BusProvider;
import me.rubl.gbandroidbase.events.ChangeThemeEvent;
import me.rubl.gbandroidbase.events.ShowAddWeatherInfoEvent;

public class SettingsFragment extends Fragment {

    Settings params;

    Toolbar toolbar;
    MaterialCheckBox cbDarkTheme;
    MaterialCheckBox cbShowDetails;

    public SettingsFragment() {
        params = Settings.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        findViews(root);
        prepareViews();

        return root;
    }

    private void findViews(View root) {
        toolbar = root.findViewById(R.id.toolbar);
        cbDarkTheme = root.findViewById(R.id.cbDarkTheme);
        cbShowDetails = root.findViewById(R.id.cbShowDetails);
    }

    private void prepareViews() {
        toolbar.setTitle(R.string.settings_title);

        cbDarkTheme.setChecked(params.isDarkTheme());
        cbShowDetails.setChecked(params.isShowWeatherDetails());

        cbDarkTheme.setOnCheckedChangeListener((v, isChecked) -> {
            params.setDarkTheme(isChecked);
            BusProvider.getInstance().post(new ChangeThemeEvent());
        });
        cbShowDetails.setOnCheckedChangeListener((v, isChecked) -> {
            params.setShowDetailsWeather(isChecked);
            BusProvider.getInstance().post(new ShowAddWeatherInfoEvent(isChecked));
        });
    }
}
