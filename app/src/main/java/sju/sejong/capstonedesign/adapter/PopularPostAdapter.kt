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

class PopularPostAdapter(private val context: Context) : RecyclerView.Adapter<PopularPostAdapter.CustomViewHolder>() {
    private val list = List<BasicResponse>(3) { BasicResponse(1,true,"") }

    inner class CustomViewHolder(private val binding: ItemPopularPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BasicResponse) {
            Glide.with(context).load(R.drawable.plants).into(binding.ivItemPopularPost)
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemPopularPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount()= 3
}