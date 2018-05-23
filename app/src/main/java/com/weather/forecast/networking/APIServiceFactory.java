package com.weather.forecast.networking;

import com.weather.forecast.application.constants.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIServiceFactory {

    static private Interceptor sNetworkInterceptor = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request.Builder builder1 = chain.request().newBuilder();
            Request request = builder1.header(Constants.CONTENT_TYPE, Constants.APP_JSON).build();
            Response response = chain.proceed(request);
            return response;
        }
    };

    public static <T> T getServiceInstance(final Class<T> tclass) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS).
                readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS).
                addInterceptor(sNetworkInterceptor);

        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(Constants.BASE_URL).
                addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync()).
                client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(tclass);
    }
}
