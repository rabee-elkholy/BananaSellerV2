package com.androdu.bananaSeller.data.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiService {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://api-new-test.bananas.ae/";

    public static IRetrofitApi getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .callTimeout(3, TimeUnit.MINUTES)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

            builder.client(httpClient.build());

            retrofit = builder.build();
        }

        return retrofit.create(IRetrofitApi.class);
    }
}
