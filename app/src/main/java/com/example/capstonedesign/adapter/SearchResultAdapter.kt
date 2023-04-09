package com.example.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign.databinding.ItemSearchResultBinding
import com.example.capstonedesign.model.CropDetailResponse
import com.example.capstonedesign.model.Item

class SearchResultAdapter(private val context: Context)
    : RecyclerView.Adapter<SearchResultAdapter.CustomViewHolder>() {
    private var searchResultList = listOf<Item>()

    inner class CustomViewHolder(private val binding: ItemSearchResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.tvItemSearchResultCropName.text = item.cropName
            binding.tvItemSearchResultName1.text = item.sickNameKor + "\n" + "(" + item.sickNameChn + ")"
            binding.tvItemSearchResultName2.text = item.sickNameEng

            Glide.with(context).load(
                item.oriImg.toString().replace("amp;", "")
            ).into(binding.ivItemSearchResult)
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(searchResultList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    fun setData(list: List<Item>) {
        searchResultList = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = searchResultList.size
}