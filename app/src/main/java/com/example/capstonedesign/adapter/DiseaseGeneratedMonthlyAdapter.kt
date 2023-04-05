package com.example.capstonedesign.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.ItemHomeAlertMonthBinding
import org.jsoup.nodes.Element

class DiseaseGeneratedMonthlyAdapter(private val context: Context, private val rvInt: Int) : RecyclerView.Adapter<DiseaseGeneratedMonthlyAdapter.CustomViewHolder>() {
    private var diseaseList = listOf<Element>()
    inner class CustomViewHolder(private val binding: ItemHomeAlertMonthBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Element) {
            binding.model = item.text().substring(4).replace(')', '-')

            if (rvInt == 2) {
                binding.recHomeAlertMonth.setBackgroundColor(Color.parseColor("#FFE500"))
            } else if (rvInt == 3) {
                binding.recHomeAlertMonth.setBackgroundColor(context.resources.getColor(R.color.main_green))
            }
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