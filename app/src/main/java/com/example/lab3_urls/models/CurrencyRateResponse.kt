package com.example.lab3_urls.models

data class CurrencyRateResponse(
    val baseCurrency: String,
    val rates: List<CurrencyRate> // rates теперь является списком
)
