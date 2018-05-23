package com.weather.forecast.weatherforecast.ui;

import android.util.Log;

import com.weather.forecast.application.ui.base.BaseViewModel;
import com.weather.forecast.weatherforecast.model.WeatherForecastResponse;
import com.weather.forecast.response.APIResponseCallback;
import com.weather.forecast.response.ResponseWrapper;
import com.weather.forecast.networking.NetworkManager;
import com.weather.forecast.utils.CommonUtils;

import retrofit2.HttpException;

public class WeatherForecastListViewModel extends BaseViewModel<WeatherForecastListNavigator> implements APIResponseCallback {

    private static String TAG = WeatherForecastListViewModel.class.getName();
    private WeatherForecastResponse mWeatherForecastResponse;

    public void getWeatherForecast() {
        NetworkManager.getInstance().getWeatherForecast(this);
    }

    public WeatherForecastResponse getWeatherForecastResponse() {
        return mWeatherForecastResponse;
    }

    @Override
    public void onSubscribe() {
        getNavigator().showLoader();
        Log.d(TAG, "onSubscribe called");
    }

    @Override
    public void onSuccess(ResponseWrapper responseWrapper) {
        Log.d(TAG, "onSuccess called");
        mWeatherForecastResponse = (WeatherForecastResponse) responseWrapper.getResponse();
        Log.d(TAG, mWeatherForecastResponse.getCod());
        getNavigator().hideLoader();
        getNavigator().updateUI(mWeatherForecastResponse);
    }

    @Override
    public void onError(Throwable error) {
        getNavigator().hideLoader();
        Log.d(TAG, "onError called");

        if (error instanceof HttpException) {
            int code = -1;
            try {
                String errorJson = ((HttpException) error).response().errorBody().string();
                if (!CommonUtils.isEmptyString(errorJson)) {
                    //Based on HTTP Error code, display specific error message to user
                    //For now, displaying generic network error message
                    getNavigator().showNoInternetDialog();
                }
            } catch (Exception e1) {
                Log.e(TAG, e1.getMessage());
            }

            //Display generic error message to user if not network error message.
            getNavigator().showUnableToProcessDialog();
        }
    }
}
