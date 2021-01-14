package me.rubl.gbandroidbase.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import me.rubl.gbandroidbase.R;

public class City implements Parcelable {

    public static final String ARGUMENT_KEY = "cityIntentKey";
    public static final String URL_FORMAT = "https://yandex.ru/pogoda/%s/details?via=ms";

    private final int nameResourceId;
    private final String nameInURL;
    private int temperatureInC;

    public City(int nameResourceId, String nameInURL, int temperatureInC) {
        this.nameResourceId = nameResourceId;
        this.nameInURL = nameInURL;
        this.temperatureInC = temperatureInC;
    }

    protected City(Parcel in) {
        nameResourceId = in.readInt();
        nameInURL = in.readString();
        temperatureInC = in.readInt();
    }

    public int getNameResourceId() {
        return nameResourceId;
    }

    public String getWeatherURL() {
        return String.format(URL_FORMAT, nameInURL);
    }

    public int getTemperatureInC() {
        return temperatureInC;
    }

    public static City getMockCity() {
        return new City(R.string.city_moscow, "moscow", 10);
    }

    public static ArrayList<City> getMockCities() {
        return new ArrayList<>(Arrays.asList(
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nameResourceId);
        dest.writeString(nameInURL);
        dest.writeInt(temperatureInC);
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
