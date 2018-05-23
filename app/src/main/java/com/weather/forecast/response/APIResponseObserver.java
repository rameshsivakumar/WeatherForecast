package com.weather.forecast.response;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class APIResponseObserver<T> implements SingleObserver<T> {

    private APIResponseCallback mAPIResponseCallback;

    public APIResponseObserver(APIResponseCallback callback) {
        this.mAPIResponseCallback = callback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (null != mAPIResponseCallback) {
            mAPIResponseCallback.onSubscribe();
        }
    }

    @Override
    public void onSuccess(T response) {
        if(mAPIResponseCallback != null) {
            ResponseWrapper<T> responseWrapper = new ResponseWrapper<>(response);
            mAPIResponseCallback.onSuccess(responseWrapper);
        }

    }

    @Override
    public void onError(Throwable e) {
        if (null != mAPIResponseCallback) {
            mAPIResponseCallback.onError(e);
        }
    }
}
