package com.example.capstonedesign.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
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
            Toast.makeText(context, "Bottom Sheet 안의 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.buttonMenu3.setOnClickListener {
            Toast.makeText(context, "Bottom Sheet 안의 버튼 클릭", Toast.LENGTH_SHORT).show()
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