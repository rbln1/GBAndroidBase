package me.rubl.gbandroidbase.events;

public class ShowAddWeatherInfoEvent {

    private final boolean isShowAddWeatherInfo;

    public ShowAddWeatherInfoEvent(boolean isShowAddWeatherInfo) {
        this.isShowAddWeatherInfo = isShowAddWeatherInfo;
    }

    public boolean isShowAddWeatherInfo() {
        return isShowAddWeatherInfo;
    }
}
