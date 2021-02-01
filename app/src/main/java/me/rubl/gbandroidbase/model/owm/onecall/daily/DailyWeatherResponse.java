package me.rubl.gbandroidbase.model.owm.onecall.daily;

public class DailyWeatherResponse {

    double lat;
    double lon;
    String timezone;
    int timezone_offset;
    Daily[] daily;

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

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTimezoneOffset() {
        return timezone_offset;
    }

    public void setTimezoneOffset(int timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public Daily[] getDaily() {
        return daily;
    }

    public void setDaily(Daily[] daily) {
        this.daily = daily;
    }
}
