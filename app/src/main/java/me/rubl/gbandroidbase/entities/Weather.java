package me.rubl.gbandroidbase.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import me.rubl.gbandroidbase.enums.WeatherType;

public class Weather implements Parcelable {

    private WeatherType type;
    private int temperatureInC;
    private Date date;

    // Additional
    private double wildSpeed;
    private int humidity;
    private int pressure;

    public Weather(WeatherType type, int temperatureInC, Date date,
                   double wildSpeed, int humidity, int pressure) {
        this.type = type;
        this.temperatureInC = temperatureInC;
        this.date = date;
        this.wildSpeed = wildSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    protected Weather(Parcel in) {
        temperatureInC = in.readInt();
        wildSpeed = in.readDouble();
        humidity = in.readInt();
        pressure = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(temperatureInC);
        dest.writeDouble(wildSpeed);
        dest.writeInt(humidity);
        dest.writeInt(pressure);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    public WeatherType getType() {
        return type;
    }

    public void setType(WeatherType type) {
        this.type = type;
    }

    public int getTemperatureInC() {
        return temperatureInC;
    }

    public void setTemperatureInC(int temperatureInC) {
        this.temperatureInC = temperatureInC;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getWildSpeed() {
        return wildSpeed;
    }

    public void setWildSpeed(double wildSpeed) {
        this.wildSpeed = wildSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public static Weather getMockCurrentWeather(int averageTemperatureInC) {
        return getMockWeather(new Date(), averageTemperatureInC);
    }

    public static Weather getMockWeather(Date date, int averageTemperatureInC) {
        Random random = new Random();
        int weatherTypeIndex = random.nextInt(3);
        WeatherType type = WeatherType.SUNNY;
        switch (weatherTypeIndex) {
            case 0:
                type = WeatherType.CLOUDY;
                break;
            case 1:
                type = WeatherType.SUNNY;
                break;
            case 2:
                type = WeatherType.RAINY;
                break;
            default:break;
        }
        int temperatureInC = (int)
                ((Math.random()
                        * ((averageTemperatureInC + 5) - (averageTemperatureInC - 5)))
                        + (averageTemperatureInC - 5));

        // скорость ветра - дабл
        double wildSpeed = Math.random() * 10;
        // влажность - инт
        int humidity = (int) (Math.random() * 100);
        // давление - инт
        int pressure = (int) (Math.random() * 1000);

        return new Weather(type, temperatureInC, date, wildSpeed, humidity, pressure);
    }

    public static Weather[] getMockDetailsWeatherForDay(Date date) {
        Weather[] weatherInHours = new Weather[24];

        for (int i = 0; i < weatherInHours.length; i++) {
            weatherInHours[i] =
                    getMockWeather(date, (int)(Math.random() * 15));
        }
        return weatherInHours;
    }

    public static Weather[] getMockWeatherForWeek(Date date) {
        Weather[] weatherForWeek = new Weather[7];

        Date startWeek = subtractDays(date, 3);

        for (int i = 0; i < weatherForWeek.length; i++) {
            weatherForWeek[i] =
                    getMockWeather(addDays(startWeek, i), (int)(Math.random() * 15));
        }
        return weatherForWeek;
    }

    public static Date addDays(Date date, int count) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, count);

        return calendar.getTime();
    }

    public static Date subtractDays(Date date, int count) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -count);

        return calendar.getTime();
    }
}
