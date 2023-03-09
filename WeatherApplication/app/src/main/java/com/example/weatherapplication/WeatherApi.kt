package com.example.weatherapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/forecast.json?key=f74e983a89614c87a24155510230703&q=Novi%20Sad&days=7&aqi=no&alerts=no")
    suspend fun getWeather():Response<Weather>
}