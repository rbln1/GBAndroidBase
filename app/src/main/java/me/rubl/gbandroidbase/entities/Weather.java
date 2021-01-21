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

    public Weather(WeatherType type, int temperatureInC, Date date) {
        this.type = type;
        this.temperatureInC = temperatureInC;
        this.date = date;
    }

    protected Weather(Parcel in) {
        type = WeatherType.valueOf(in.readString());
        temperatureInC = in.readInt();
        date = new Date(in.readLong());
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

        return new Weather(type, temperatureInC, date);
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

        Random random = new Random();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type.name());
        dest.writeInt(temperatureInC);
        dest.writeLong(date.getTime());
    }
}
