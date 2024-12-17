package com.example.lab3_urls
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://v78qr.wiremockapi.cloud/"

    // Создание Retrofit-экземпляра
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Подключаем Gson для конвертации в модели
            .build()
            .create(ApiService::class.java)
    }
}
