package ru.rudn.weatherandroidapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://api.weatherapi.com/";
    private static Network Instance;

    public Network() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Network getInstance() {
        if (Instance == null) {
            Instance = new Network();
        }
        return Instance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
