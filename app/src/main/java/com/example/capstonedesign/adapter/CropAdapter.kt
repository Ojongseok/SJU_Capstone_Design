package com.example.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign.databinding.ItemCropBinding
import org.jsoup.nodes.Element

class CropAdapter(private val context: Context, private val cropImgList: List<String>) : RecyclerView.Adapter<CropAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private val url = "https://ncpms.rda.go.kr/npms/"
    private var cropList = listOf<Element>()

    inner class CustomViewHolder(private val binding: ItemCropBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Element, img: String) {
            Glide.with(context).load(img).into(binding.ivItemCrop)
            binding.tvItemCropName.text = item.select("img.imgBord").attr("alt").toString()
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(cropList[position], cropImgList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemCropBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setData(list: List<Element>) {
        cropList = list
        notifyDataSetChanged()
    }

    fun getCropName(position: Int): String {
        return cropList[position].select("img.imgBord").attr("alt").toString()
    }

    override fun getItemCount()= cropList.size
}