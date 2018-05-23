package com.weather.forecast.weatherforecast.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecastResponse {

    @SerializedName("city")
    private City city;

    @SerializedName("cod")
    private String cod;

    @SerializedName("message")
    private double message;

    @SerializedName("cnt")
    private int cnt;

    @SerializedName("list")
    private List<WeatherData> weatherList;

    public City getCity() {
        return city;
    }

    public String getCod() {
        return cod;
    }

    public double getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public List<WeatherData> getWeatherList() {
        return weatherList;
    }

    public static class City {

        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("coord")
        private Coordinate coord;

        @SerializedName("country")
        private String country;

        @SerializedName("population")
        private long population;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Coordinate getCoord() {
            return coord;
        }

        public String getCountry() {
            return country;
        }

        public long getPopulation() {
            return population;
        }
    }

    public static class Coordinate {
        @SerializedName("lon")
        private double lon;

        @SerializedName("lat")
        private double lat;

        public double getLon() {
            return lon;
        }

        public double getLat() {
            return lat;
        }
    }

    public static class WeatherData implements Parcelable{
        @SerializedName("dt")
        private long dt;

        @SerializedName("temp")
        private Temp temp;

        @SerializedName("pressure")
        private double pressure;

        @SerializedName("humidity")
        private double humidity;

        @SerializedName("weather")
        private List<Weather> weather = new ArrayList<>();

        @SerializedName("speed")
        private double speed;

        @SerializedName("deg")
        private int deg;

        @SerializedName("clouds")
        private int clouds;

        public long getDt() {
            return dt;
        }

        public Temp getTemp() {
            return temp;
        }

        public double getPressure() {
            return pressure;
        }

        public double getHumidity() {
            return humidity;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public double getSpeed() {
            return speed;
        }

        public int getDeg() {
            return deg;
        }

        public int getClouds() {
            return clouds;
        }

        public WeatherData(Parcel parcel) {
            dt = parcel.readLong();
            temp = parcel.readParcelable(Temp.class.getClassLoader());
            pressure = parcel.readDouble();
            humidity = parcel.readDouble();
            parcel.readTypedList(weather, Weather.CREATOR);
            speed = parcel.readDouble();
            deg = parcel.readInt();
            clouds = parcel.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(dt);
            dest.writeParcelable(temp, flags);
            dest.writeDouble(pressure);
            dest.writeDouble(humidity);
            dest.writeTypedList(weather);
            dest.writeDouble(speed);
            dest.writeInt(deg);
            dest.writeInt(clouds);
        }

        public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
            @Override
            public WeatherData createFromParcel(Parcel parcel) {
                return new WeatherData(parcel);
            }

            @Override
            public WeatherData[] newArray(int i) {
                return new WeatherData[1];
            }
        };
    }

    public static class Temp implements Parcelable{
        @SerializedName("day")
        private float day;

        @SerializedName("min")
        private double min;

        @SerializedName("max")
        private double max;

        @SerializedName("night")
        private float night;

        @SerializedName("eve")
        private float eve;

        @SerializedName("morn")
        private float morn;

        public float getDay() {
            return day;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }

        public float getNight() {
            return night;
        }

        public float getEve() {
            return eve;
        }

        public float getMorn() {
            return morn;
        }

        public Temp(Parcel in) {
            day = in.readFloat();
            min = in.readDouble();
            max = in.readDouble();
            night = in.readFloat();
            eve = in.readFloat();
            morn = in.readFloat();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(day);
            dest.writeDouble(min);
            dest.writeDouble(max);
            dest.writeFloat(night);
            dest.writeFloat(eve);
            dest.writeFloat(morn);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Temp> CREATOR = new Creator<Temp>() {
            @Override
            public Temp createFromParcel(Parcel parcel) {
                return new Temp(parcel);
            }

            @Override
            public Temp[] newArray(int i) {
                return new Temp[1];
            }
        };


    }

    public static class Weather implements Parcelable{
        @SerializedName("id")
        private int id;

        @SerializedName("main")
        private String main;

        @SerializedName("description")
        private String description;

        @SerializedName("icon")
        private String icon;

        public int getId() {
            return id;
        }

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        public Weather(Parcel in) {
            id = in.readInt();
            main = in.readString();
            description = in.readString();
            icon = in.readString();
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(main);
            dest.writeString(description);
            dest.writeString(icon);
        }

        public static final Creator<Weather> CREATOR = new Creator<Weather>() {
            @Override
            public Weather createFromParcel(Parcel parcel) {
                return new Weather(parcel);
            }

            @Override
            public Weather[] newArray(int i) {
                return new Weather[1];
            }
        };

    }

}
