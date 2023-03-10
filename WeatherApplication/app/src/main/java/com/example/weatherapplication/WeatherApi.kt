package com.example.weatherapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/forecast.json?")
    suspend fun getWeather(
        @Query("key") key:String,
        @Query("q") city: String,
        @Query("days") days: Int = 7,
        @Query("aqi") aqi: String ="no",
        @Query("alerts") alerts: String = "no"
    ): Response<Weather>
}