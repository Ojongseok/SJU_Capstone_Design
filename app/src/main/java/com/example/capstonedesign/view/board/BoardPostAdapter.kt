package com.example.capstonedesign.view.board

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.PostTest
import com.example.capstonedesign.databinding.ItemRequestPostBinding

class BoardPostAdapter(private val context: Context, private val list: List<PostTest>)
    : RecyclerView.Adapter<BoardPostAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemRequestPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostTest) {
            binding.ivPostThumbnail
            binding.tvPostThumbnailTitle.text = item.title
            binding.tvPostThumbnailContents.text = item.contents
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemRequestPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount()= list.size
}