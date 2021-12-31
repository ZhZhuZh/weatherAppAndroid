package ru.rudn.weatherandroidapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/v1/current.json")
    Call<Weather> getCurrentWeather(@Query("key") String key, @Query("q") String q, @Query("lang") String lang);
}
