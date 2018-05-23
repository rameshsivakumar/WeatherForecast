package com.weather.forecast.networking;

import com.weather.forecast.services.WeatherForecastAPIService;
import com.weather.forecast.weatherforecast.model.WeatherForecastResponse;
import com.weather.forecast.response.APIResponseCallback;
import com.weather.forecast.response.APIResponseObserver;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NetworkManager {

    private static NetworkManager sNetworkManager;
    private WeatherForecastAPIService mWeatherForecastAPIService;

    private NetworkManager() {
        mWeatherForecastAPIService = APIServiceFactory.getServiceInstance(WeatherForecastAPIService.class);
    }

    public static synchronized NetworkManager getInstance() {
        if(sNetworkManager == null) {
            sNetworkManager = new NetworkManager();
        }
        return sNetworkManager;
    }

    public void getWeatherForecast(APIResponseCallback apiResponseCallback) {
        executeSingle(mWeatherForecastAPIService.getWeatherForecast(), new APIResponseObserver<WeatherForecastResponse>(apiResponseCallback));
    }

    private <T> void executeSingle(Single<T> single, SingleObserver<T> observer) {
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
