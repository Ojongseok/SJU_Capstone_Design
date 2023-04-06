package com.example.capstonedesign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.capstonedesign.databinding.DialogBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: DialogBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonMenu1.setOnClickListener{
            val action = BottomSheetDialogDirections.actionDialogBottomSheetToFragmentPlantsInspect()
            findNavController().navigate(action)
        }

        binding.buttonMenu2.setOnClickListener{
            val action = BottomSheetDialogDirections.actionDialogBottomSheetToFragmentPostWrite()
            findNavController().navigate(action)
        }

        binding.buttonMenu3.setOnClickListener {
            val action = BottomSheetDialogDirections.actionDialogBottomSheetToFragmentSearchDisease()
            findNavController().navigate(action)
        }

        binding.buttonMenu4.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}