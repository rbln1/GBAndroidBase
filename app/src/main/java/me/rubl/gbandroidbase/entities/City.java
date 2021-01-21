package me.rubl.gbandroidbase.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

import me.rubl.gbandroidbase.R;

public class City implements Parcelable {

    public static final String ARGUMENT_KEY = "cityIntentKey";
    public static final String URL_FORMAT = "https://yandex.ru/pogoda/%s/details?via=ms";

    private static ArrayList<City> mockCitiesList;

    private final int nameResourceId;
    private final String nameInURL;
    private int averageTemperatureInC;
    private Weather weatherCurrent;
    private Weather[] weatherOnDay;
    private Weather[] weatherOnWeek;

    public City(int nameResourceId, String nameInURL, int averageTemperatureInC) {
        this.nameResourceId = nameResourceId;
        this.nameInURL = nameInURL;
        this.averageTemperatureInC = averageTemperatureInC;

        weatherCurrent = Weather.getMockCurrentWeather(averageTemperatureInC);
        weatherOnDay = Weather.getMockDetailsWeatherForDay(weatherCurrent.getDate());
        weatherOnWeek = Weather.getMockWeatherForWeek(weatherCurrent.getDate());
    }

    protected City(Parcel in) {
        nameResourceId = in.readInt();
        nameInURL = in.readString();
        averageTemperatureInC = in.readInt();

        weatherCurrent = Weather.getMockCurrentWeather(averageTemperatureInC);
        weatherOnDay = Weather.getMockDetailsWeatherForDay(weatherCurrent.getDate());
        weatherOnWeek = Weather.getMockWeatherForWeek(weatherCurrent.getDate());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nameResourceId);
        dest.writeString(nameInURL);
        dest.writeInt(averageTemperatureInC);
    }

    public int getNameResourceId() {
        return nameResourceId;
    }

    public String getWeatherURL() {
        return String.format(URL_FORMAT, nameInURL);
    }

    public int getAverageTemperatureInC() {
        return averageTemperatureInC;
    }

    public Weather getWeatherCurrent() {
        return weatherCurrent;
    }

    public Weather[] getWeatherOnDay() {
        return weatherOnDay;
    }

    public Weather[] getWeatherOnWeek() {
        return weatherOnWeek;
    }

    public static City getMockCity() {
        return getMockCities().get(0);
    }

    public static ArrayList<City> getMockCities() {
        if (mockCitiesList == null || mockCitiesList.size() == 0) {
            mockCitiesList = new ArrayList<>(Arrays.asList(
                    new City(R.string.city_moscow, "moscow", 10),
                    new City(R.string.city_hong_kong, "hong-kong", 14),
                    new City(R.string.city_bangkok, "bangkok", 18),
                    new City(R.string.city_singapore, "singapore", -3),
                    new City(R.string.city_london, "london", 7),
                    new City(R.string.city_paris, "paris", 11),
                    new City(R.string.city_dubai, "dubai", 22),
                    new City(R.string.city_delhi, "delhi", 15)
            ));
        }
        return mockCitiesList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
