package com.example.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.databinding.ItemCommentBinding
import com.example.capstonedesign.databinding.ItemRequestPostBinding
import com.example.capstonedesign.model.board.AllCommentResult
import com.example.capstonedesign.model.board.ContentList
import com.example.capstonedesign.util.Constants.MEMBER_ID
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(private val context: Context) : RecyclerView.Adapter<CommentAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var commentsList = listOf<AllCommentResult>()

    inner class CustomViewHolder(private val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllCommentResult) {
            binding.tvItemCommentNickname.text = item.nickname
            binding.tvItemCommentContents.text = item.content
            binding.tvItemCommentDate.text = item.modifiedDate.removeRange(16,19)

            if (item.memberId == MEMBER_ID) {
                binding.btnItemCommentDelete.visibility = View.VISIBLE
            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(commentsList[position])

        holder.itemView.btn_item_comment_delete.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setData(list: List<AllCommentResult>) {
        commentsList = list
        notifyDataSetChanged()
    }

    override fun getItemCount()= commentsList.size
}