package com.example.lab3_urls

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.gridlayout.widget.GridLayout
import android.widget.Toast
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.lab3_urls.models.CurrencyRate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Найдите GridLayout по ID
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

        // Загружаем курсы валют
        fetchCurrencyRates(gridLayout)

        // Запускаем обновление данных каждую минуту
        startRefreshingData(gridLayout)
    }

    // Функция для загрузки данных с API
    private fun fetchCurrencyRates(gridLayout: GridLayout) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getExchangeRates()

                if (response.rates.isNullOrEmpty()) {
                    Log.e("MainActivity", "Ошибка: Нет данных о курсах валют.")
                } else {
                    // Выводим данные в лог
                    Log.d("MainActivity", "Получены данные о курсах: ${response.rates}")
                }

                // Очищаем GridLayout перед добавлением новых данных
                gridLayout.removeAllViews()

                // Для каждой валюты создаем карточку
                response.rates?.forEach { currencyRate ->
                    addCurrencyCard(gridLayout, currencyRate)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Ошибка при загрузке данных", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Функция для добавления карточки валюты
    private fun addCurrencyCard(gridLayout: GridLayout, currencyRate: CurrencyRate) {
        val cardView = LayoutInflater.from(this).inflate(R.layout.card_item, gridLayout, false)

        val currencyNameTextView = cardView.findViewById<TextView>(R.id.currency_name)
        val currentRateTextView = cardView.findViewById<TextView>(R.id.current_rate)

        currencyNameTextView.text = currencyRate.currency
        currentRateTextView.text = currencyRate.currentRate.toString()

        // Обработка клика по карточке
        cardView.setOnClickListener {
            // Передаем данные о валюте и ее исторические курсы в HistoryActivity
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra("currency_name", currencyRate.currency)
                putExtra("history_rates", ArrayList(currencyRate.averageRatesLast5Days))
            }
            startActivity(intent)
        }

        gridLayout.addView(cardView)
    }

    // Функция для обновления данных каждую минуту
    private fun startRefreshingData(gridLayout: GridLayout) {
        lifecycleScope.launch {
            while (true) {
                fetchCurrencyRates(gridLayout) // Загружаем обновленные данные
                Log.d("MainActivity startRefreshingData", "Получены данные о курсах")
                delay(60000) // Задержка в 60 секунд
            }
        }
    }
}
