package com.example.capstonedesign.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.databinding.ItemHomeAlertMonthBinding
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class AlertMonthAdapter(private val list: Elements)
    : RecyclerView.Adapter<AlertMonthAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemHomeAlertMonthBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Element) {
            binding.tvHomeAlertMonth.text = item.text().substring(4).replace(')', '-')

        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemHomeAlertMonthBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount()= list.size
}