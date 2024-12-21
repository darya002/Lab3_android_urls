package com.example.lab3_urls

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3_urls.adapters.HistoryAdapter
import com.example.lab3_urls.models.AverageRate

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val currencyName = intent.getStringExtra("currency_name")
        val historyRates = intent.getSerializableExtra("history_rates") as ArrayList<AverageRate>

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = HistoryAdapter(historyRates)
        recyclerView.adapter = adapter
    }
}
