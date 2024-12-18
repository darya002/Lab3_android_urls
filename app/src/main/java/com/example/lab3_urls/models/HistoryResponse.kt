package com.example.lab3_urls.models

data class HistoryResponse(
    val rates: Map<String, List<Double>>, // Исторические курсы для валюты
    val date: String
)