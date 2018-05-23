package com.weather.forecast.weatherforecastdetail.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weather.forecast.R;
import com.weather.forecast.application.ui.base.BaseActivity;
import com.weather.forecast.databinding.ActivityWeatherForecastDetailBinding;
import com.weather.forecast.utils.DateUtils;
import com.weather.forecast.utils.WeatherUtils;
import com.weather.forecast.weatherforecast.model.WeatherForecastResponse;

public class WeatherForecastDetailActivity extends BaseActivity<ActivityWeatherForecastDetailBinding> implements WeatherForecastDetailNavigator {

    WeatherForecastDetailViewModel mWeatherForecastDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        WeatherForecastResponse.WeatherData weatherData = getIntent().getParcelableExtra("weatherData");
        if(weatherData != null) {
            mWeatherForecastDetailViewModel.setWeatherForecastData(weatherData);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weather_forecast_detail;
    }

    @Override
    protected void setDataBinding() {
        mWeatherForecastDetailViewModel = ViewModelProviders.of(this).get(WeatherForecastDetailViewModel.class);
        mWeatherForecastDetailViewModel.setContext(this);
        mWeatherForecastDetailViewModel.setNavigator(this);

    }

    @Override
    public void updateUI() {
        WeatherForecastResponse.WeatherData weatherData = mWeatherForecastDetailViewModel.getWeatherForecastData();
        if(weatherData == null) {
            return;
        }

        int weatherId = weatherData.getWeather().get(0).getId();

        int weatherImageId = WeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);

        getViewDataBinding().primaryInfo.weatherIcon.setImageResource(weatherImageId);

        long localDateMidnightGmt = weatherData.getDt();
        String dateText = DateUtils.getFriendlyDateString(this, localDateMidnightGmt, true);

        getViewDataBinding().primaryInfo.date.setText(dateText);

        String description = WeatherUtils.getStringForWeatherCondition(this, weatherId);

        String descriptionA11y = getString(R.string.a11y_forecast, description);

        getViewDataBinding().primaryInfo.weatherDescription.setText(description);
        getViewDataBinding().primaryInfo.weatherDescription.setContentDescription(descriptionA11y);

        getViewDataBinding().primaryInfo.weatherIcon.setContentDescription(descriptionA11y);

        double highInCelsius = weatherData.getTemp().getMax();

        String highString = WeatherUtils.formatTemperature(this, highInCelsius);

        String highA11y = getString(R.string.a11y_high_temp, highString);

        getViewDataBinding().primaryInfo.highTemperature.setText(highString);
        getViewDataBinding().primaryInfo.highTemperature.setContentDescription(highA11y);

        double lowInCelsius = weatherData.getTemp().getMin();

        String lowString = WeatherUtils.formatTemperature(this, lowInCelsius);

        String lowA11y = getString(R.string.a11y_low_temp, lowString);

        getViewDataBinding().primaryInfo.lowTemperature.setText(lowString);
        getViewDataBinding().primaryInfo.lowTemperature.setContentDescription(lowA11y);

        double humidity = weatherData.getHumidity();
        String humidityString = getString(R.string.format_humidity, humidity);

        String humidityA11y = getString(R.string.a11y_humidity, humidityString);

        getViewDataBinding().extraDetails.humidity.setText(humidityString);
        getViewDataBinding().extraDetails.humidity.setContentDescription(humidityA11y);

        getViewDataBinding().extraDetails.humidityLabel.setContentDescription(humidityA11y);

        double windSpeed = weatherData.getSpeed();
        float windDirection = weatherData.getDeg();
        String windString = WeatherUtils.getFormattedWind(this, windSpeed, windDirection);

        String windA11y = getString(R.string.a11y_wind, windString);

        getViewDataBinding().extraDetails.windMeasurement.setText(windString);
        getViewDataBinding().extraDetails.windMeasurement.setContentDescription(windA11y);

        getViewDataBinding().extraDetails.windLabel.setContentDescription(windA11y);

        double pressure = weatherData.getPressure();
        String pressureString = getString(R.string.format_pressure, pressure);

        String pressureA11y = getString(R.string.a11y_pressure, pressureString);

        getViewDataBinding().extraDetails.pressure.setText(pressureString);
        getViewDataBinding().extraDetails.pressure.setContentDescription(pressureA11y);

        getViewDataBinding().extraDetails.pressureLabel.setContentDescription(pressureA11y);

    }
}
