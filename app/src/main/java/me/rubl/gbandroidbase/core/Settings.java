package me.rubl.gbandroidbase.core;

public class Settings {

    private static Settings instance;

    private boolean isDarkTheme = false;
    private boolean showDetailsWeather = false;

    private Settings() {}

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
}
