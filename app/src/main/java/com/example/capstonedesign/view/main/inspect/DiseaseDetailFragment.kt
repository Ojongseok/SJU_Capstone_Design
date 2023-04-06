package com.example.capstonedesign.view.main.inspect

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.capstonedesign.R
import com.example.capstonedesign.adapter.CropDetailInfoAdapter
import com.example.capstonedesign.databinding.FragmentDiseaseDetailBinding
import com.example.capstonedesign.databinding.FragmentLoginBinding
import com.example.capstonedesign.view.main.CropDetailInfoFragmentArgs
import com.example.capstonedesign.viewmodel.OpenApiViewModel

class DiseaseDetailFragment: Fragment() {
    private var _binding: FragmentDiseaseDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DiseaseDetailFragmentArgs>()
    private val viewModel: OpenApiViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDiseaseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        setObserver()
    }

    private fun setObserver() {
        viewModel.diseaseDetailInfo.observe(viewLifecycleOwner) {
            Log.d("tag", it.toString())
        }
        viewModel.diseaseDetailInfoCompleted.observe(viewLifecycleOwner) {

        }
    }

    private fun initData() {
        val sickKey = args.sickKey
        viewModel.searchDiseaseDetailInfo(sickKey)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}