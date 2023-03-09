package com.example.weatherapplication

data class Weather(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)
