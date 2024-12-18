package com.example.lab3_urls.models

data class CurrencyRate(
    val currency: String,
    val currentRate: Double,
    val averageRatesLast5Days: List<AverageRate>
)