package com.example.myapplication;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weatherapi {
    @GET("weather")
    Call<Getmain> getweather(@Query("q") String cityname, @Query("appid")String api_key);



}
