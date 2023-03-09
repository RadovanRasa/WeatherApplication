package com.example.weatherapplication

data class Forecastday(

    val date: String,
    val date_epoch: Int,
    val day: Day,
    val astro: Astro,
    val hour: List<Hour>
)
