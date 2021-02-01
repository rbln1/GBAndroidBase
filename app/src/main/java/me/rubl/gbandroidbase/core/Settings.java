package me.rubl.gbandroidbase.core;

import me.rubl.gbandroidbase.model.core.City;

public class Settings {

    private static Settings instance;

    private boolean isDarkTheme = false;
    private boolean showDetailsWeather = false;
    private City currentCity;

    private Settings() {
        currentCity = getDefaultCity();
    }

    private City getDefaultCity() {
        return new City("Москва", 55.7522, 37.6156);
    }

    public static Settings getInstance() {
        if (instance == null) instance = new Settings();
        return instance;
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        isDarkTheme = darkTheme;
    }

    public boolean isShowWeatherDetails() {
        return showDetailsWeather;
    }

    public void setShowDetailsWeather(boolean showDetailsWeather) {
        this.showDetailsWeather = showDetailsWeather;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }
}
