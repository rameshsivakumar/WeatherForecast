package com.weather.forecast.application.ui.base;

import android.arch.lifecycle.ViewModel;
import android.content.Context;


public class BaseViewModel<N> extends ViewModel {

    private N navigator;
    private Context context;

    public N getNavigator() {
        return navigator;
    }

    public void setNavigator(N navigator) {
        this.navigator = navigator;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
