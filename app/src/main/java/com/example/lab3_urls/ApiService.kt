package com.example.lab3_urls

import com.example.lab3_urls.models.CurrencyRateResponse
import retrofit2.http.GET

interface ApiService {
    // Получаем текущие курсы валют с историей
    @GET("exchange-rates")
    suspend fun getExchangeRates(): CurrencyRateResponse
}
