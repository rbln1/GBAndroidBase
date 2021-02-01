package me.rubl.gbandroidbase.model.owm.onecall.daily;

import me.rubl.gbandroidbase.model.owm.onecall.Weather;

public class Daily {

    int dt;
    int sunrise;
    int sunset;
    Temp temp;
    FeelsLike feels_like;
    int pressure;
    int humidity;
    double dew_point;
    double wind_speed;
    int wind_deg;
    Weather[] weather;
    int clouds;
    double pop;
    double uvi;
    double snow;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public FeelsLike getFeelsLike() {
        return feels_like;
    }

    public void setFeelsLike(FeelsLike feels_like) {
        this.feels_like = feels_like;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getDewPoint() {
        return dew_point;
    }

    public void setDewPoint(double dew_point) {
        this.dew_point = dew_point;
    }

    public double getWindSpeed() {
        return wind_speed;
    }

    public void setWindSpeed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getWindDeg() {
        return wind_deg;
    }

    public void setWindDeg(int wind_deg) {
        this.wind_deg = wind_deg;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    public double getUvi() {
        return uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }
}
