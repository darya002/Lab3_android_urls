package com.example.lab3_urls.models

import java.io.Serializable

data class HistoryRate(
    val date: String,
    val rate: Float
) : Serializable

