package com.example.lab3_urls.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3_urls.R
import com.example.lab3_urls.models.AverageRate

class HistoryAdapter(private val historyRates: List<AverageRate>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val rateTextView: TextView = itemView.findViewById(R.id.rateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyRates[position]
        holder.dateTextView.text = item.date
        holder.rateTextView.text = item.rate.toString()
    }

    override fun getItemCount(): Int = historyRates.size
}
