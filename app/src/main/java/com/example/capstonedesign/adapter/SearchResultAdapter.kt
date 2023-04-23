package com.example.capstonedesign.adapter

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign.databinding.ItemSearchResultBinding
import com.example.capstonedesign.model.openapi.Item


class SearchResultAdapter(private val context: Context, private val keyword: String)
    : RecyclerView.Adapter<SearchResultAdapter.CustomViewHolder>() {
    private var searchResultList = listOf<Item>()
    private lateinit var itemClickListener: OnItemClickListener

    inner class CustomViewHolder(private val binding: ItemSearchResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.tvItemSearchResultCropName.text = item.cropName
            binding.tvItemSearchResultName2.text = item.sickNameEng
//            binding.tvItemSearchResultName1.text = item.sickNameKor + "\n" + "(" + item.sickNameChn + ")"

//            val content = item.sickNameKor!!
//            val spannableString = SpannableString(content)
//            val start = content.indexOf(keyword)
//            val end = start + keyword.length
//            spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#37C64B")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//            binding.tvItemSearchResultName1.text = spannableString
            binding.tvItemSearchResultName1.text = item.sickNameKor

            Glide.with(context).load(
                item.oriImg.toString().replace("amp;", "")
            ).into(binding.ivItemSearchResult)
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(searchResultList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    fun setData(list: List<Item>) {
        searchResultList = list
        notifyDataSetChanged()
    }

    fun getSickKey(position: Int) : String {
        return searchResultList[position].sickKey.toString()
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    override fun getItemCount() = searchResultList.size
}