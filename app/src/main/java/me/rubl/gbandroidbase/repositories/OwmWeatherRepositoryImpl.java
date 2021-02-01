package me.rubl.gbandroidbase.repositories;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import me.rubl.gbandroidbase.BuildConfig;
import me.rubl.gbandroidbase.model.owm.onecall.daily.DailyWeatherResponse;
import me.rubl.gbandroidbase.model.owm.onecall.hourly.HourlyWeatherResponse;
import me.rubl.gbandroidbase.model.owm.weather.WeatherResponse;

public class OwmWeatherRepositoryImpl implements WeatherRepository{

    private static final String TAG = "OWM_REPOSITORY_IMPL_TAG";
    private final String API_KEY;
    private static final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s";
    private static final String ONECALL_DAILY_URL =
            "https://api.openweathermap.org/data/2.5/onecall?" +
                    "lat=%.2f&lon=%.2f&exclude=current,minutely,hourly,alert&units=metric&appid=%s";
    private static final String ONECALL_HOURLY_URL =
            "https://api.openweathermap.org/data/2.5/onecall?" +
                    "lat=%.2f&lon=%.2f&exclude=current,minutely,daily,alert&units=metric&appid=%s";

    public OwmWeatherRepositoryImpl() {
        API_KEY = BuildConfig.WEATHER_API_KEY;
    }

    @Override
    public WeatherResponse getWeatherByCityName(String cityName) {
        if (cityName == null || cityName.trim().isEmpty()) return null;
        cityName = cityName.trim().replace(" ", "%20");
        try {
            final URL uri = new URL(String.format(WEATHER_URL, cityName, API_KEY));
            Log.d(TAG, uri.toString());
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String result = getLines(in);
                Gson gson = new Gson();
                return gson.fromJson(result, WeatherResponse.class);
            } catch (Exception e) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
                return null;
            } finally {
                if (null != urlConnection) {
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DailyWeatherResponse getDailyWeatherByLatLon(double lat, double lon) {
        try {
            final URL uri = new URL(String.format(ONECALL_DAILY_URL, lat, lon, API_KEY));
            Log.d(TAG, uri.toString());
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String result = getLines(in);
                Gson gson = new Gson();
                return gson.fromJson(result, DailyWeatherResponse.class);
            } catch (Exception e) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
                return null;
            } finally {
                if (null != urlConnection) {
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HourlyWeatherResponse getHourlyWeatherByLayLon(double lat, double lon) {
        try {
            final URL uri = new URL(String.format(ONECALL_HOURLY_URL, lat, lon, API_KEY));
            Log.d(TAG, uri.toString());
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String result = getLines(in);
                Gson gson = new Gson();
                return gson.fromJson(result, HourlyWeatherResponse.class);
            } catch (Exception e) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
                return null;
            } finally {
                if (null != urlConnection) {
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
        return null;
    }

    private String getLines(BufferedReader in) {
        StringBuilder rawData = new StringBuilder(1024);
        String tempVariable;

        while(true) {
            try {
                tempVariable = in.readLine();
                if (tempVariable == null) break;
                rawData.append(tempVariable).append("\n");
            } catch (IOException e) {
                Log.e(TAG, "Fail getLines", e);
                e.printStackTrace();
            }
        }

        try {
            in.close();
        } catch (IOException e) {
            Log.e(TAG, "Fail stream close", e);
            e.printStackTrace();
        }

        return rawData.toString();
    }
}
