package me.rubl.gbandroidbase.repositories;

import me.rubl.gbandroidbase.model.owm.onecall.daily.DailyWeatherResponse;
import me.rubl.gbandroidbase.model.owm.onecall.hourly.HourlyWeatherResponse;
import me.rubl.gbandroidbase.model.owm.weather.WeatherResponse;

public interface WeatherRepository {

    WeatherResponse getWeatherByCityName(String cityName);

    DailyWeatherResponse getDailyWeatherByLatLon(double lat, double lon);

    HourlyWeatherResponse getHourlyWeatherByLayLon(double lat, double lon);

}
