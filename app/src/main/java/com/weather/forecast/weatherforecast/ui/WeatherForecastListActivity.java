package com.weather.forecast.weatherforecast.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.weather.forecast.R;
import com.weather.forecast.databinding.ActivityWeatherForecastListBinding;
import com.weather.forecast.weatherforecast.adapter.WeatherForecastListAdapter;
import com.weather.forecast.weatherforecast.model.WeatherForecastResponse;
import com.weather.forecast.application.ui.base.BaseActivity;
import com.weather.forecast.utils.DialogUtils;
import com.weather.forecast.weatherforecastdetail.ui.WeatherForecastDetailActivity;

public class WeatherForecastListActivity extends BaseActivity<ActivityWeatherForecastListBinding> implements WeatherForecastListNavigator, WeatherForecastListAdapter.WeatherForecastCallback {

    private static String TAG = WeatherForecastListActivity.class.getName();
    private WeatherForecastListViewModel mWeatherForecastListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if(mWeatherForecastListViewModel.getWeatherForecastResponse() == null) {
            Log.d(TAG, "getWeatherForecast called");
            mWeatherForecastListViewModel.getWeatherForecast();
        } else {
            Log.d(TAG, "initWeatherForecastUI called");
            initWeatherForecastUI(mWeatherForecastListViewModel.getWeatherForecastResponse());
        }
    }

    private void initWeatherForecastUI(WeatherForecastResponse weatherForecastResponse) {
        RecyclerView recyclerView = getViewDataBinding().weatherForecastRecyclerview;
        recyclerView.setAdapter(new WeatherForecastListAdapter(this, this, weatherForecastResponse));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weather_forecast_list;
    }

    @Override
    protected void setDataBinding() {
        mWeatherForecastListViewModel = ViewModelProviders.of(this).get(WeatherForecastListViewModel.class);
        mWeatherForecastListViewModel.setContext(this);
        mWeatherForecastListViewModel.setNavigator(this);
    }

    @Override
    public void updateUI(WeatherForecastResponse weatherForecastResponse) {
        initWeatherForecastUI(weatherForecastResponse);
    }

    @Override
    public void selectedForecastItem(WeatherForecastResponse.WeatherData weatherData) {
        Intent forecastDetailIntent = new Intent(this, WeatherForecastDetailActivity.class);
        forecastDetailIntent.putExtra("weatherData", weatherData);
        startActivity(forecastDetailIntent);
    }

    @Override
    public void showLoader() {
        showProgressDialog(R.string.retrieving_weather_forecast);
    }

    @Override
    public void hideLoader() {
        hideProgressDialog();
    }

    @Override
    public void showNoInternetDialog() {
        showNoInternetMessage();
    }

    @Override
    public void showUnableToProcessDialog() {
        DialogUtils.getAlertDialog(WeatherForecastListActivity.this, -1, getResources().getString(R.string.api_error_occured), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }, true, R.style.alert_dialog_theme).show();
    }
}
