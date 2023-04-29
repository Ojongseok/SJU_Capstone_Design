package com.example.capstonedesign.view.board

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.FragmentMainBinding
import com.example.capstonedesign.databinding.FragmentTermsBinding

class TermsFragment: Fragment() {
    private var _binding: FragmentTermsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTermsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnTermsComplete.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.textView4.movementMethod = ScrollingMovementMethod()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}