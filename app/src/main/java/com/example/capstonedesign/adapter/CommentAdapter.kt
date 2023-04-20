package com.example.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.databinding.ItemRequestPostBinding
import com.example.capstonedesign.model.board.ContentList

class CommentAdapter(private val context: Context) : RecyclerView.Adapter<CommentAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private val list = mutableListOf<ContentList>()

    inner class CustomViewHolder(private val binding: ItemRequestPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentList) {

        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

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