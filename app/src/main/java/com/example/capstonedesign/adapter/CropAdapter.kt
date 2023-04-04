package com.example.capstonedesign.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign.databinding.ItemCropBinding
import com.example.capstonedesign.model.PostTest
import com.example.capstonedesign.databinding.ItemRequestPostBinding
import com.example.capstonedesign.viewmodel.OpenApiViewModel
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class CropAdapter(private val context: Context) : RecyclerView.Adapter<CropAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private val url = "https://ncpms.rda.go.kr/npms/"
    private var cropList = listOf<Element>()

    inner class CustomViewHolder(private val binding: ItemCropBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Element) {
            if (position == 1) {
                Log.d("tag",url + item.select("img.imgBord").attr("src"))
//                Log.d("tag",item.select("img.imgBord").attr("alt"))
//                Log.d("tag",item.select("img.imgBord").toString())
            }
            Glide.with(context).load(url + item.select("img.imgBord").attr("src")).into(binding.ivItemCrop)
            binding.tvItemCropName.text = item.select("img.imgBord").attr("alt").toString()
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(cropList[position])

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