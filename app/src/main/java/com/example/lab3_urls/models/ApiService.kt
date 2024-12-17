package com.example.lab3_urls.models

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("exchange-rates")
    fun getCurrencyRates(): Call<CurrencyRateResponse>
}