package com.example.capstonedesign.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign.databinding.ItemVirusImgBinding
import com.example.capstonedesign.model.openapi.ImageListItem
import com.example.capstonedesign.view.main.directory.ImageActivity


class VirusImgAdapter(
    private val context: Context,
    private val activity: Activity,
    private val virusImgList: List<ImageListItem>
) : RecyclerView.Adapter<VirusImgAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemVirusImgBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageListItem) {
            val img = item.image.toString().replace("amp;","")
            Glide.with(context).load(img).into(binding.ivItemVirusImg)

            binding.ivItemVirusImg.setOnClickListener {
                val intent = Intent(context, ImageActivity::class.java)

                intent.putExtra("img", img)
                val pairThumb : Pair<View, String> = Pair.create(it, it.transitionName)
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairThumb)
                context.startActivity(intent, optionsCompat.toBundle());
            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(virusImgList[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemVirusImgBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount()= virusImgList.size
}