package com.example.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign.databinding.ItemVirusImgBinding
import com.example.capstonedesign.model.ImageListItem

class VirusImgAdapter(
    private val context: Context,
    private val virusImgList: List<ImageListItem>
) : RecyclerView.Adapter<VirusImgAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemVirusImgBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageListItem) {
            Glide.with(context).load(
                item.image.toString().replace("amp;","")
            ).into(binding.ivItemVirusImg)
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(virusImgList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemVirusImgBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount()= virusImgList.size
}