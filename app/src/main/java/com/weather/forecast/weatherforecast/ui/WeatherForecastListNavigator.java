package com.weather.forecast.weatherforecast.ui;

import com.weather.forecast.weatherforecast.model.WeatherForecastResponse;

/**
 * Created by ramesh siva kumar on 5/21/2018.
 */

public interface WeatherForecastListNavigator {

    public void showLoader();

    public void hideLoader();

    public void showNoInternetDialog();

    public void showUnableToProcessDialog();

    public void updateUI(WeatherForecastResponse weatherForecastResponse);
}
