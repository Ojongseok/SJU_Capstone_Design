package com.example.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign.databinding.ItemPesticideInfoBinding
import com.example.capstonedesign.model.openapi.PesticideItem
import kotlinx.android.synthetic.main.item_pesticide_info.view.*
import org.jsoup.nodes.Element

class PesticideInfoAdapter(private val context: Context) : RecyclerView.Adapter<PesticideInfoAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var pesticideList = listOf<PesticideItem>()

    inner class CustomViewHolder(private val binding: ItemPesticideInfoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PesticideItem) {
            binding.tvPesticideInfoNameKor.text = item.pestiKorName
            binding.tvPesticideInfoPesti.text = item.pestiBrandName
            binding.tvPesticideInfoPestiUse.text = item.engName
            binding.tvPesticideInfoCompany.text = item.compName
            binding.tvPesticideInfoTag.text = item.useName
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(pesticideList[position])

        holder.itemView.btn_pesticide_info_detail.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemPesticideInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    fun setData(list: List<PesticideItem>) {
        pesticideList = list
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    override fun getItemCount() = pesticideList.size
}