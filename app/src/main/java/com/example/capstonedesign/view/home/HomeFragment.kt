package com.example.capstonedesign.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign.adapter.DiseaseGeneratedMonthlyAdapter
import com.example.capstonedesign.databinding.FragmentHomeBinding
import com.example.capstonedesign.viewmodel.OpenApiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newCoroutineContext

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var diseaseGeneratedMonthlyAdapter1: DiseaseGeneratedMonthlyAdapter
    private lateinit var diseaseGeneratedMonthlyAdapter2: DiseaseGeneratedMonthlyAdapter
    private lateinit var diseaseGeneratedMonthlyAdapter3: DiseaseGeneratedMonthlyAdapter
    private val viewModel: OpenApiViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        initData()
        setObserver()
        setRv()
    }

    private fun setRv() {
        binding.rvHomeAlertMonth2.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = diseaseGeneratedMonthlyAdapter2
        }

        binding.rvHomeAlertMonth3.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = diseaseGeneratedMonthlyAdapter3
        }
    }

    private fun setObserver() {
        viewModel.diseaseGeneratedMonthly2.observe(viewLifecycleOwner) {
            diseaseGeneratedMonthlyAdapter2.setData(it)

            binding.tvHomeAlert2.text = it.size.toString()
        }

        viewModel.diseaseGeneratedMonthly3.observe(viewLifecycleOwner) {
            diseaseGeneratedMonthlyAdapter3.setData(it)

            binding.tvHomeAlert3.text = it.size.toString()
        }

        viewModel.pbHome.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbHomeAlertMonth.visibility = View.GONE
            }
        }
    }

    private fun initData() {
        viewModel.setDiseaseGeneratedMonthly()

        diseaseGeneratedMonthlyAdapter2 = DiseaseGeneratedMonthlyAdapter()
        diseaseGeneratedMonthlyAdapter3 = DiseaseGeneratedMonthlyAdapter()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}