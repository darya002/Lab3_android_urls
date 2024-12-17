package com.example.lab3_urls

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.gridlayout.widget.GridLayout
import android.widget.Toast
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.lab3_urls.models.AverageRate
import com.example.lab3_urls.models.CurrencyRate
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Найдите GridLayout по ID
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

        // Загружаем курсы валют
        fetchCurrencyRates(gridLayout)
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
        val inflater = LayoutInflater.from(this)
        val cardView = inflater.inflate(R.layout.card_item, gridLayout, false) as CardView

        // Настроим карточку
        val currencyName = cardView.findViewById<TextView>(R.id.currency_name)
        val currentRate = cardView.findViewById<TextView>(R.id.current_rate)

        // Отображаем информацию о валюте
        currencyName.text = currencyRate.currency
        currentRate.text = currencyRate.currentRate.toString()

        // Выводим информацию в лог
        Log.d("MainActivity", "Добавлена карточка для валюты: ${currencyRate.currency} с курсом: ${currencyRate.currentRate}")

        // Устанавливаем слушатель кликов на карточку
        cardView.setOnClickListener {
            // Например, показываем сообщение с названием валюты
            val message = "Нажали на валюту: ${currencyRate.currency}"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            // Передаем валюту в другую активность
            val intent = Intent(this@MainActivity, HistoryActivity::class.java).apply {
                putExtra("currency_name", currencyRate.currency)
                putExtra("history_rates", ArrayList(currencyRate.averageRatesLast5Days)) // Преобразуем в ArrayList
            }
            startActivity(intent)
        }

        // Добавляем карточку в GridLayout
        gridLayout.addView(cardView)

        // Выводим информацию о добавленной карточке в лог
        Log.d("MainActivity", "Карточка добавлена в GridLayout")
    }
}
