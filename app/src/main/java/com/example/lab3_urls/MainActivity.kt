package com.example.lab3_urls

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
import com.google.android.material.snackbar.Snackbar
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
                // Выполняем запрос к API
                val response = RetrofitInstance.api.getExchangeRates()

                // Логируем полный ответ
                Log.d("MainActivity", "Ответ от API: $response")

                // Проверяем, что данные не пустые
                if (response.rates.isNullOrEmpty()) {
                    Log.e("MainActivity", "Пустые данные в ответе: $response")
                    showSnackbar("Ошибка: Нет данных о курсах валют.")
                    return@launch
                }

                // Если данные корректны, очищаем GridLayout и добавляем карточки
                Log.d("MainActivity", "Данные корректны, обновляем GridLayout.")
                gridLayout.removeAllViews()

                response.rates.forEach { currencyRate ->
                    Log.d("MainActivity", "Добавление карточки для валюты: ${currencyRate.currency}")
                    addCurrencyCard(gridLayout, currencyRate)
                }

                // Показываем сообщение об успешной загрузке
                showSnackbar("Данные успешно обновлены!")
            } catch (e: Exception) {
                // Обработка исключений
                Log.e("MainActivity", "Ошибка загрузки данных: ${e.message}", e)
                showSnackbar("Ошибка при загрузке данных: ${e.localizedMessage}")
            }
        }
    }

    private fun addCurrencyCard(gridLayout: GridLayout, currencyRate: CurrencyRate) {
        val cardView = LayoutInflater.from(this).inflate(R.layout.card_item, gridLayout, false)

        val currencySymbolTextView = cardView.findViewById<TextView>(R.id.currency_symbol)
        val currencyNameTextView = cardView.findViewById<TextView>(R.id.currency_name)
        val currentRateTextView = cardView.findViewById<TextView>(R.id.current_rate)

        // Устанавливаем текст для символа, имени валюты и текущего курса
        currencySymbolTextView.text = getCurrencySymbol(currencyRate.currency)
        currencyNameTextView.text = currencyRate.currency
        currentRateTextView.text = currencyRate.currentRate.toString()

        // Обработка клика по карточке
        cardView.setOnClickListener {
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
                Log.d("MainActivity startRefreshingData", "Данные обновлены")
                delay(60000) // Задержка в 60 секунд
            }
        }
    }

    // Вспомогательная функция для показа Snackbar
    private fun showSnackbar(message: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }
}
