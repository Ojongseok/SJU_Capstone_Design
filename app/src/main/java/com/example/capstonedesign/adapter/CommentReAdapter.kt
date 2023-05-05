package com.example.capstonedesign.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.databinding.ItemCommentBinding
import com.example.capstonedesign.databinding.ItemCommentReBinding
import com.example.capstonedesign.databinding.ItemRequestPostBinding
import com.example.capstonedesign.model.BasicResponse
import com.example.capstonedesign.model.board.AllCommentResult
import com.example.capstonedesign.model.board.ContentList
import com.example.capstonedesign.model.board.ReCommentList
import com.example.capstonedesign.util.Constants.MEMBER_ID
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_comment_re.view.*

class CommentReAdapter(private val context: Context) : RecyclerView.Adapter<CommentReAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var reCommentsList = listOf<ReCommentList>()

    inner class CustomViewHolder(private val binding: ItemCommentReBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReCommentList) {
            binding.tvItemCommentNickname.text = item.nickname
            binding.tvItemCommentContents.text = item.content
            binding.tvItemCommentDate.text = item.createdDate.removeRange(16,19)

            if (item.memberId == MEMBER_ID) {
                binding.ltItemCommentMenu.visibility = View.VISIBLE
            } else {
                binding.ltItemCommentMenu.visibility = View.GONE
            }

        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(reCommentsList[position])

        holder.itemView.btn_item_comment_re_delete.setOnClickListener {
            itemClickListener.onClickDelete(it, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemCommentReBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    interface OnItemClickListener {
        fun onClickDelete(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setData(list: List<ReCommentList>) {
        reCommentsList = list
        notifyDataSetChanged()
    }

    override fun getItemCount()= reCommentsList.size
}