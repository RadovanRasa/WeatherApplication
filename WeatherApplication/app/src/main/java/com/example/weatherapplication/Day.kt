package com.example.weatherapplication

data class Day(

    val maxtemp_c: Double,
    val mintemp_c: Double,
    val avgtemp_c: Double,
    val totalsnow_cm: Double,
    val condition: Condition
)
