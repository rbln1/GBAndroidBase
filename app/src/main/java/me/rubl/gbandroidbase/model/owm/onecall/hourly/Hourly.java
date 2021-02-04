package me.rubl.gbandroidbase.model.owm.onecall.hourly;

import me.rubl.gbandroidbase.model.owm.onecall.Weather;

public class Hourly {

    int dt;
    double temp;
    double feels_like;
    int pressure;
    int humidity;
    double dew_point;
    double uvi;
    int clouds;
    int visibility;
    double wind_speed;
    int wind_deg;
    Weather[] weather;
    double pop;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feels_like;
    }

    public void setFeelsLike(double feels_like) {
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

    public double getUvi() {
        return uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
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

    public double getPop() {
        return pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }
}
