package com.example.roadanomalydetector

data class Report(
    val name: String? = null,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val imageUrl: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
