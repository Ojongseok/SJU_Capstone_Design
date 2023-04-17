package com.example.capstonedesign.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.capstonedesign.databinding.FragmentPesticideBinding
import com.example.capstonedesign.viewmodel.OpenApiViewModel

class PesticideFragment: Fragment() {
    private var _binding: FragmentPesticideBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OpenApiViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPesticideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPesticideInfo("가지","갈색무늬병")
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}