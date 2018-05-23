package com.weather.forecast.application.constants;

public interface Constants {

    int CONNECTION_TIMEOUT = 20;
    int READ_TIMEOUT = 20;

    String CONTENT_TYPE ="Content-Type";
    String APP_JSON= "application/json";
    String BASE_URL = "https://andfun-weather.udacity.com";

    int VIEW_TYPE_TODAY = 0;
    int VIEW_TYPE_FUTURE_DAY = 1;
}
