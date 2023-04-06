package com.example.capstonedesign.view.home

import android.os.Bundle
import android.sax.Element
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign.R
import com.example.capstonedesign.adapter.DiseaseGeneratedMonthlyAdapter
import com.example.capstonedesign.databinding.FragmentHomeBinding
import com.example.capstonedesign.model.CropDetailResponse
import com.example.capstonedesign.viewmodel.OpenApiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newCoroutineContext

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var diseaseGeneratedMonthlyAdapter1: DiseaseGeneratedMonthlyAdapter
    private lateinit var diseaseGeneratedMonthlyAdapter2: DiseaseGeneratedMonthlyAdapter
    private lateinit var diseaseGeneratedMonthlyAdapter3: DiseaseGeneratedMonthlyAdapter
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

        binding.rvHomeAlertMonth3.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = diseaseGeneratedMonthlyAdapter3
        }
    }

    private fun setObserver() {
        viewModel.diseaseGeneratedMonthly2.observe(viewLifecycleOwner) {
            diseaseGeneratedMonthlyAdapter2.setData(it)
        }

        viewModel.diseaseGeneratedMonthly3.observe(viewLifecycleOwner) {
            diseaseGeneratedMonthlyAdapter3.setData(it)
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

        viewModel.setDiseaseGeneratedMonthly().isCompleted

        diseaseGeneratedMonthlyAdapter2 = DiseaseGeneratedMonthlyAdapter(requireContext(), 2)
        diseaseGeneratedMonthlyAdapter3 = DiseaseGeneratedMonthlyAdapter(requireContext(), 3)
    }
}