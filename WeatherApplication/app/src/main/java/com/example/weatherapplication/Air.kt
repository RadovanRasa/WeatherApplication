package com.example.weatherapplication

data class Air(
    val co: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    val pm2_5: Double,
    val pm10: Double,
    val us_epa_index: Double,
    val gb_defra_index: Double
)
