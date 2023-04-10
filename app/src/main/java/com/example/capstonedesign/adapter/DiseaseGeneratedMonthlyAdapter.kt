package com.example.capstonedesign.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.ItemHomeAlertMonthBinding
import org.jsoup.nodes.Element

class DiseaseGeneratedMonthlyAdapter(private val context: Context, private val rvInt: Int) : RecyclerView.Adapter<DiseaseGeneratedMonthlyAdapter.CustomViewHolder>() {
    private var diseaseList = listOf<Element>()
    private lateinit var itemClickListener: OnItemClickListener

    inner class CustomViewHolder(private val binding: ItemHomeAlertMonthBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Element) {
            binding.model = item.text().substring(item.text().indexOf('-')+1).replace(')', '-')

            if (rvInt == 2) {
                binding.recHomeAlertMonth.setBackgroundColor(Color.parseColor("#FFE500"))
            } else if (rvInt == 3) {
                binding.recHomeAlertMonth.setBackgroundColor(context.resources.getColor(R.color.main_green))
            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(diseaseList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemHomeAlertMonthBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    fun getDiseaseName(position: Int): Pair<String, String> {
        val originalText =
            diseaseList[position].text().substring(diseaseList[position].text().indexOf('-') + 1)
                .replace(')', '-')
        val cropName = originalText.split('-')[0]
        val diseaseName = originalText.split('-')[1]

        return Pair(cropName, diseaseName)
    }

    fun setData(list: List<Element>) {
        diseaseList = list
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount()= diseaseList.size
}