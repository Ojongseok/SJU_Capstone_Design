package com.example.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.ItemCropBinding
import com.example.capstonedesign.databinding.ItemHomeRegionGeneratedDiseaseBinding
import org.jsoup.nodes.Element

class RegionGeneratedAdapter(private val context: Context) : RecyclerView.Adapter<RegionGeneratedAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    val list = List<String>(17) { "" }
    val regionArray = context.resources.getStringArray(R.array.signup_select_region)

    inner class CustomViewHolder(private val binding: ItemHomeRegionGeneratedDiseaseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvHomeRegionName.text = regionArray[position]
            binding.tvHomeRegionGeneratedCount.text = position.toString()
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemHomeRegionGeneratedDiseaseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    override fun getItemCount() = 17
}