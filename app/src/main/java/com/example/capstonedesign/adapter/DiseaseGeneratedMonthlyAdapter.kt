package com.example.capstonedesign.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.databinding.ItemHomeAlertMonthBinding
import org.jsoup.nodes.Element

class DiseaseGeneratedMonthlyAdapter() : RecyclerView.Adapter<DiseaseGeneratedMonthlyAdapter.CustomViewHolder>() {
    private var diseaseList = listOf<Element>()
    inner class CustomViewHolder(private val binding: ItemHomeAlertMonthBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Element) {
            binding.model = item.text().substring(4).replace(')', '-')
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(diseaseList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemHomeAlertMonthBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    fun setData(list: List<Element>) {
        diseaseList = list
        notifyDataSetChanged()
    }

    override fun getItemCount()= diseaseList.size
}