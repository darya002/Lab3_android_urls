package com.example.lab3_urls.models
data class CurrencyRateResponse(
    val baseCurrency: String,
    val rates: List<CurrencyRate>
)

data class CurrencyRate(
    val currency: String,
    val currentRate: Float,
    val averageRatesLast5Days: List<AverageRate>
)

data class AverageRate(
    val date: String,
    val rate: Float
)
