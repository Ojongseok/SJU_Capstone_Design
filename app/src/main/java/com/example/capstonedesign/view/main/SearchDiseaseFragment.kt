package com.example.capstonedesign.view.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.FragmentLoginBinding
import com.example.capstonedesign.databinding.FragmentSearchDiseaseBinding
import com.example.capstonedesign.retrofit.OpenApiRetrofitInstance.API_KEY
import com.example.capstonedesign.retrofit.OpenApiRetrofitInstance.OpenApiRetrofitService
import com.example.capstonedesign.retrofit.OpenApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchDiseaseFragment: Fragment() {
    private var _binding: FragmentSearchDiseaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchDiseaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val response = OpenApiRetrofitService.searchDisease(API_KEY,"SVC01","AA001","상추")
            withContext(Dispatchers.Main) {
                Log.d("tag",response.body()?.toString()!!)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}