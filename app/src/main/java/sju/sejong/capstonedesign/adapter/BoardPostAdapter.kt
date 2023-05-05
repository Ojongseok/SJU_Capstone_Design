package sju.sejong.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sju.sejong.capstonedesign.R
import sju.sejong.capstonedesign.databinding.ItemRequestPostBinding
import sju.sejong.capstonedesign.model.board.ContentList

class BoardPostAdapter(private val context: Context, private val list: List<ContentList>)
    : RecyclerView.Adapter<BoardPostAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    inner class CustomViewHolder(private val binding: ItemRequestPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentList) {
            binding.model = item
            Glide.with(context).load(item.image).fallback(R.drawable.plants).into(binding.ivPostThumbnail)
            binding.tvPostThumbnailDate.text = item.createdDate.removeRange(16,19)

            if (item.tag == "QUESTION") {
                if (item.isSolved) {
                    binding.tvItemPostSolveTag.text = "해결완료"
                    binding.tvItemPostSolveTag.setBackgroundResource(R.drawable.background_rec_8dp_green)
                } else {
                    binding.tvItemPostSolveTag.text = "미해결"
                }
                binding.tvItemPostSolveTag.visibility = View.VISIBLE
            } else {
                binding.tvItemPostSolveTag.visibility = View.GONE
            }
        }
    }

    override fun onBindViewHolder(holder: BoardPostAdapter.CustomViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardPostAdapter.CustomViewHolder {
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