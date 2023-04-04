package com.example.capstonedesign.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.capstonedesign.adapter.CropAdapter
import com.example.capstonedesign.databinding.FragmentSearchCropInfoBinding
import com.example.capstonedesign.util.GridSpaceItemDecoration
import com.example.capstonedesign.viewmodel.OpenApiViewModel

class SearchCropInfoFragment: Fragment() {
    private var _binding: FragmentSearchCropInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OpenApiViewModel by viewModels()
    private lateinit var cropAdapter: CropAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchCropInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        initData()
        setObserver()
        setRv()
    }

    private fun setRv() {
        binding.rvSearchDisease.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = cropAdapter
            // spanCount -> grid 수, space->여백
            val spanCount = 3
            val space = 20
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }

        cropAdapter.setItemClickListener(object : CropAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val cropName = cropAdapter.getCropName(position)
                val action = SearchCropInfoFragmentDirections.actionFragmentSearchCropInfoToFragmentCropDetailInfo(cropName)
                findNavController().navigate(action)

            }
        })
    }

    private fun setObserver() {
        viewModel.cropList.observe(viewLifecycleOwner) {
            cropAdapter.setData(it)
        }
    }

    private fun initData() {
        viewModel.setCropList()
        cropAdapter = CropAdapter(requireContext())
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}