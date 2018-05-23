package com.weather.forecast.weatherforecastdetail.ui;

import com.weather.forecast.application.ui.base.BaseViewModel;
import com.weather.forecast.weatherforecast.model.WeatherForecastResponse;


public class WeatherForecastDetailViewModel extends BaseViewModel<WeatherForecastDetailNavigator> {

    private WeatherForecastResponse.WeatherData mWeatherForecastData;

    public WeatherForecastResponse.WeatherData getWeatherForecastData() {
        return mWeatherForecastData;
    }

    public void setWeatherForecastData(WeatherForecastResponse.WeatherData mWeatherForecastData) {
        this.mWeatherForecastData = mWeatherForecastData;
        getNavigator().updateUI();
    }
}
