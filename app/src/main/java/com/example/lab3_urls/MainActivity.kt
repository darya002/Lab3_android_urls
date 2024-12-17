package com.example.lab3_urls

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.gridlayout.widget.GridLayout
import com.example.lab3_urls.models.CurrencyRateResponse
import com.example.lab3_urls.models.CurrencyRate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var gridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)

        // Запуск асинхронного запроса
        fetchCurrencyRates()
    }

    private fun fetchCurrencyRates() {
        RetrofitInstance.api.getCurrencyRates().enqueue(object : Callback<CurrencyRateResponse> {
            override fun onResponse(call: Call<CurrencyRateResponse>, response: Response<CurrencyRateResponse>) {
                if (response.isSuccessful) {
                    val currencyRates = response.body()?.rates
                    if (currencyRates != null) {
                        // Прокачка всех валют с их текущими курсами
                        runOnUiThread {
                            currencyRates.forEach { currency ->
                                addCurrencyCard(currency.currency, currency.currentRate.toString())
                            }
                        }
                    }
                } else {
                    // Обработка ошибки (например, Toast или Snackbar)
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Ошибка при получении данных", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<CurrencyRateResponse>, t: Throwable) {
                // В случае ошибки
                runOnUiThread {
                    Toast.makeText(applicationContext, "Ошибка при подключении к серверу", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun addCurrencyCard(currencyName: String, currencyValue: String) {
        // Инфлейтинг карточки
        val cardView = layoutInflater.inflate(R.layout.card_item, gridLayout, false) as CardView
        val currencyNameTextView: TextView = cardView.findViewById(R.id.currency_name)
        val currentRateTextView: TextView = cardView.findViewById(R.id.current_rate)

        // Устанавливаем данные для карточки
        currencyNameTextView.text = currencyName
        currentRateTextView.text = currencyValue

        // Добавляем карточку в GridLayout
        gridLayout.addView(cardView)
    }
}
