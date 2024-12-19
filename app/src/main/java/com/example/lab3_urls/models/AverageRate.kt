package com.example.lab3_urls.models

import java.io.Serializable

data class AverageRate(
    val date: String,
    val rate: Double
) : Serializable