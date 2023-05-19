package sju.sejong.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sju.sejong.capstonedesign.databinding.ItemCropBinding
import org.jsoup.nodes.Element
import sju.sejong.capstonedesign.R
import sju.sejong.capstonedesign.databinding.ItemPopularPostBinding
import sju.sejong.capstonedesign.model.BasicResponse
import sju.sejong.capstonedesign.model.board.AllPostResponse
import sju.sejong.capstonedesign.model.board.ContentList
import sju.sejong.capstonedesign.model.board.PopularPostList
import sju.sejong.capstonedesign.model.board.PopularPostResponse

class PopularPostAdapter(private val context: Context) : RecyclerView.Adapter<PopularPostAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var list = listOf<PopularPostList>()

    inner class CustomViewHolder(private val binding: ItemPopularPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularPostList) {
            Glide.with(context).load(item.image).fallback(R.drawable.plants).into(binding.ivItemPopularPost)
            binding.tvItemPopularPostNickname.text = "by. " + item.nickname
            binding.tvItemPopularPostTitle.text = item.title
            binding.tvItemPopularPostContent.text = item.content
            binding.tvItemPopularPostDate.text =item.createdDate.removeRange(16,19)
            binding.tvItemPopularCommentCnt.text = item.commentNum.toString()
            binding.tvItemPopularFavoriteCount.text = item.likeNum.toString()

        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemPopularPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setData(newData: List<PopularPostList>) {
        list = newData
        notifyDataSetChanged()
    }

    override fun getItemCount()= list.size
}