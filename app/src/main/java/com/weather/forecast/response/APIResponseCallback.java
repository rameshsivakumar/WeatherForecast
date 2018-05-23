package com.weather.forecast.response;

public interface APIResponseCallback {

    /**
     * Method to notify API success.
     * @param responseWrapper instance of @{@link ResponseWrapper}, wrapping actual response.
     */
    void onSuccess(ResponseWrapper responseWrapper);

    /**
     * Method to notify Error.
     * @param error, instance of @{@link Throwable}.
     */
    void onError(Throwable error);

    /**
     * Method to notify subscribe event i.e beginning of network communication.
     */
    void onSubscribe();
}
