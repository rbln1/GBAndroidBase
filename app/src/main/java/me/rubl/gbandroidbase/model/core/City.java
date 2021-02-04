package me.rubl.gbandroidbase.model.core;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {

    String city;
    double lat;
    double lon;

    public City(String city, double lat, double lon) {
        this.city = city;
        this.lat = lat;
        this.lon = lon;
    }

    protected City(Parcel in) {
        city = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
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

    public String getName() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
