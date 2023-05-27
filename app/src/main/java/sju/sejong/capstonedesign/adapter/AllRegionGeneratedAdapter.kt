package sju.sejong.capstonedesign.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sju.sejong.capstonedesign.R
import sju.sejong.capstonedesign.databinding.ItemHomeRegionGeneratedDiseaseBinding
import sju.sejong.capstonedesign.model.board.PopularPostList
import sju.sejong.capstonedesign.model.login.AllRegionDiseaseResponse
import sju.sejong.capstonedesign.model.login.RegionDiseaseResult
import sju.sejong.capstonedesign.viewmodel.LoginViewModel

class AllRegionGeneratedAdapter(private val context: Context) : RecyclerView.Adapter<AllRegionGeneratedAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var list = listOf<RegionDiseaseResult>()

    val regionArray = context.resources.getStringArray(R.array.signup_select_region)

    inner class CustomViewHolder(private val binding: ItemHomeRegionGeneratedDiseaseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RegionDiseaseResult) {
            binding.tvHomeRegionName.text = regionArray[position]
            binding.tvHomeRegionGeneratedCount.text = item.diseaseCount.toString()
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemHomeRegionGeneratedDiseaseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setData(newData : List<RegionDiseaseResult>) {
        list = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}