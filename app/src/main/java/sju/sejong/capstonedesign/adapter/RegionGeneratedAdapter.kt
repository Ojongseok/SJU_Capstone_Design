package sju.sejong.capstonedesign.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.preferencesOf
import androidx.recyclerview.widget.RecyclerView
import sju.sejong.capstonedesign.R
import sju.sejong.capstonedesign.databinding.ItemHomeRegionGeneratedDiseaseBinding
import sju.sejong.capstonedesign.viewmodel.LoginViewModel

class RegionGeneratedAdapter(
    private val context: Context,
    private val viewModel: LoginViewModel) : RecyclerView.Adapter<RegionGeneratedAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    val regionArray = context.resources.getStringArray(R.array.signup_select_region)

    inner class CustomViewHolder(private val binding: ItemHomeRegionGeneratedDiseaseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvHomeRegionName.text = regionArray[position]
            binding.tvHomeRegionGeneratedCount.text = position.toString()
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(regionArray[position])

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
    override fun getItemCount() = 17
}