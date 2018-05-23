package com.weather.forecast.services;

import com.weather.forecast.weatherforecast.model.WeatherForecastResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface WeatherForecastAPIService {
    @GET("/weather")
    Single<WeatherForecastResponse> getWeatherForecast();
}
