package com.example.lab3_urls.models

import android.view.WindowInsetsAnimation
import android.widget.Toast
import com.example.lab3_urls.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import java.io.Serializable

// Модели данных, которые будут использоваться для истории
data class CurrencyRateResponse(
    val baseCurrency: String,
    val rates: List<CurrencyRate> // rates теперь является списком
)

data class CurrencyRate(
    val currency: String,
    val currentRate: Double,
    val averageRatesLast5Days: List<AverageRate>
)

data class AverageRate(
    val date: String,
    val rate: Double // Используем Double для точности
) : Serializable