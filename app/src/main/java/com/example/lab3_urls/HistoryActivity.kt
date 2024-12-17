package com.example.lab3_urls

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3_urls.adapters.HistoryAdapter
import com.example.lab3_urls.models.AverageRate

class HistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.recyclerViewHistory)

        // Получаем данные из Intent
        val currencyName = intent.getStringExtra("currency_name") ?: ""
        val historyRates = intent.getSerializableExtra("history_rates") as? ArrayList<AverageRate>

        if (historyRates != null && historyRates.isNotEmpty()) {
            // Если данные получены, устанавливаем адаптер
            adapter = HistoryAdapter(historyRates)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        } else {
            // Если данные не получены, показываем Toast
            Toast.makeText(this, "Исторические данные не получены", Toast.LENGTH_SHORT).show()
        }

        // Устанавливаем заголовок активности
        title = "История курсов: ${currencyName.ifEmpty { "Неизвестная валюта" }}"
    }
}