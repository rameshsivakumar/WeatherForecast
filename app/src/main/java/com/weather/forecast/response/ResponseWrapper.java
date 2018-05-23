package com.weather.forecast.response;

public final class ResponseWrapper<T> {

    /**
     * Variable holding API response.
     */
    private final T mResponse;

    public ResponseWrapper(T mResponse) {
        this.mResponse = mResponse;
    }

    public T getResponse() {
        return mResponse;
    }

    public Class<T> getResponseClassType() {
        return (Class<T>) mResponse.getClass();
    }
}
