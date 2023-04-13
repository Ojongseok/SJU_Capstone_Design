package com.example.capstonedesign.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign.R
import com.example.capstonedesign.adapter.DiseaseGeneratedMonthlyAdapter
import com.example.capstonedesign.adapter.RegionGeneratedAdapter
import com.example.capstonedesign.databinding.FragmentHomeBinding
import com.example.capstonedesign.util.GridSpaceItemDecoration
import com.example.capstonedesign.viewmodel.OpenApiViewModel
import java.util.*

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var diseaseGeneratedMonthlyAdapter1: DiseaseGeneratedMonthlyAdapter
    private lateinit var diseaseGeneratedMonthlyAdapter2: DiseaseGeneratedMonthlyAdapter
    private lateinit var diseaseGeneratedMonthlyAdapter3: DiseaseGeneratedMonthlyAdapter
    private lateinit var diseaseGeneratedRegionAdapter: RegionGeneratedAdapter
    private val viewModel: OpenApiViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDataSettings()
        setObserver()
        setRv()
    }

    private fun setRv() {
        binding.rvHomeAlertMonth2.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = diseaseGeneratedMonthlyAdapter2
        }
        diseaseGeneratedMonthlyAdapter2.setItemClickListener(object : DiseaseGeneratedMonthlyAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val pair = diseaseGeneratedMonthlyAdapter2.getDiseaseName(position)
                viewModel.getSickKey(pair.first, pair.second)
            }
        })

        binding.rvHomeAlertMonth3.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = diseaseGeneratedMonthlyAdapter3
        }
        diseaseGeneratedMonthlyAdapter3.setItemClickListener(object : DiseaseGeneratedMonthlyAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val pair = diseaseGeneratedMonthlyAdapter3.getDiseaseName(position)
                viewModel.getSickKey(pair.first, pair.second)
            }
        })

        binding.rvHomeRegion.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = diseaseGeneratedRegionAdapter
            addItemDecoration(GridSpaceItemDecoration(requireContext(), 2))
        }
        diseaseGeneratedRegionAdapter.setItemClickListener(object : RegionGeneratedAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setObserver() {
        viewModel.diseaseGeneratedMonthly2.observe(viewLifecycleOwner) {
            diseaseGeneratedMonthlyAdapter2.setData(it)
        }

        viewModel.diseaseGeneratedMonthly3.observe(viewLifecycleOwner) {
            diseaseGeneratedMonthlyAdapter3.setData(it)
        }

        viewModel.singleSickKey.observe(viewLifecycleOwner) {
            if (it == "null") {
                Toast.makeText(requireContext(), "등록되지 않은 질병입니다.", Toast.LENGTH_SHORT).show()
            } else {
                val action = HomeFragmentDirections.actionFragmentHomeToFragmentDiseaseDetail(it!!)
                findNavController().navigate(action)
            }
        }

        viewModel.pbHome.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbHomeAlertMonth.visibility = View.GONE
            }
        }
    }

    private fun initDataSettings() {
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.setDiseaseGeneratedMonthly()

        diseaseGeneratedMonthlyAdapter2 = DiseaseGeneratedMonthlyAdapter(requireContext(), 2)
        diseaseGeneratedMonthlyAdapter3 = DiseaseGeneratedMonthlyAdapter(requireContext(), 3)
        diseaseGeneratedRegionAdapter = RegionGeneratedAdapter(requireContext())
    }
}