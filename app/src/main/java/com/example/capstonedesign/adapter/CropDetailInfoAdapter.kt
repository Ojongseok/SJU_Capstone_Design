package com.example.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign.databinding.ItemCropDetailInfoBinding
import com.example.capstonedesign.model.openapi.Item

class CropDetailInfoAdapter(private val context: Context) : RecyclerView.Adapter<CropDetailInfoAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var cropInfoList = listOf<Item>()

    inner class CustomViewHolder(private val binding: ItemCropDetailInfoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            Glide.with(context).load(
                item.oriImg.toString().replace("amp;", "")
            ).into(binding.ivCropDetailInfoThumb)

            binding.tvCropDetailInfoName.text = item.sickNameKor
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(cropInfoList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemCropDetailInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setData(list: List<Item>) {
        cropInfoList = list
        notifyDataSetChanged()
    }

    override fun getItemCount()= cropInfoList.size
}