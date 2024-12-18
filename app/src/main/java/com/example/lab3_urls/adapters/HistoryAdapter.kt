package com.example.lab3_urls.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3_urls.R
import com.example.lab3_urls.models.AverageRate

class HistoryAdapter(private val historyList: List<AverageRate>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    // Создаем ViewHolder для каждого элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    // Привязываем данные к каждому элементу
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentHistory = historyList[position]
        holder.dateTextView.text = currentHistory.date
        holder.rateTextView.text = currentHistory.rate.toString()
    }

    // Возвращаем количество элементов
    override fun getItemCount(): Int {
        return historyList.size
    }

    // ViewHolder для элементов списка
    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val rateTextView: TextView = itemView.findViewById(R.id.rateTextView)
    }
}
