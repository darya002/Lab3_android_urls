package com.example.lab3_urls

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.gridlayout.widget.GridLayout
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.lab3_urls.models.CurrencyRate
import com.example.lab3_urls.web.RetrofitInstance
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)

        fetchCurrencyRates()
        startRefreshingData()
    }

    private fun fetchCurrencyRates() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getExchangeRates()

                Log.d("MainActivity", "Ответ от API: $response")

                if (response.rates.isEmpty()) {
                    Log.e("MainActivity", "Пустые данные в ответе: $response")
                    showSnackbar("Ошибка: Нет данных о курсах валют.")
                    return@launch
                }

                Log.d("MainActivity", "Данные корректны, обновляем GridLayout.")
                gridLayout.removeAllViews()
                response.rates.forEach { currencyRate ->
                    Log.d("MainActivity", "Добавление карточки для валюты: ${currencyRate.currency}")
                    addCurrencyCard(currencyRate)
                }

                showSnackbar("Данные успешно обновлены!")
            } catch (e: Exception) {
                Log.e("MainActivity", "Ошибка загрузки данных: ${e.message}", e)
                showSnackbar("Ошибка при загрузке данных: ${e.localizedMessage}")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addCurrencyCard(currencyRate: CurrencyRate) {
        val cardView = LayoutInflater.from(this).inflate(R.layout.card_item, gridLayout, false)

        val currencySymbolTextView = cardView.findViewById<TextView>(R.id.currency_symbol)
        val currencyNameTextView = cardView.findViewById<TextView>(R.id.currency_name)
        val currentRateTextView = cardView.findViewById<TextView>(R.id.current_rate)

        currencySymbolTextView.text = getCurrencySymbol(currencyRate.currency)
        currencyNameTextView.text = currencyRate.currency
        currentRateTextView.text = currencyRate.currentRate.toString()

        cardView.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra("currency_name", currencyRate.currency)
                putExtra("history_rates", ArrayList(currencyRate.averageRatesLast5Days))
            }
            startActivity(intent)
        }

        gridLayout.addView(cardView)
    }

    private fun startRefreshingData() {
        lifecycleScope.launch {
            while (true) {
                Log.d("MainActivity", "Запуск обновления данных...")
                fetchCurrencyRates() // Загружаем новые данные
                delay(60000) // Ожидаем 60 секунд
            }
        }
    }

    private fun showSnackbar(message: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }
}
