package com.example.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.model.PostTest
import com.example.capstonedesign.databinding.ItemRequestPostBinding

class BoardPostAdapter(private val context: Context, private val list: List<PostTest>)
    : RecyclerView.Adapter<BoardPostAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    inner class CustomViewHolder(private val binding: ItemRequestPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostTest) {
            binding.ivPostThumbnail
            binding.tvPostThumbnailTitle.text = item.title
            binding.tvPostThumbnailContents.text = item.contents
            binding.tvPostThumbnailDate.text = "2023-03-22 13:27"
            binding.tvPostThumbnailFavoriteCount.text = "3"
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemRequestPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount()= list.size
}