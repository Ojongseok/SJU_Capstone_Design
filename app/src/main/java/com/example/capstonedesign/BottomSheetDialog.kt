package com.example.capstonedesign

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.capstonedesign.databinding.DialogBottomSheetBinding
import com.example.capstonedesign.databinding.FragmentBoardBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog(context: Context) : BottomSheetDialogFragment() {
    private var _binding: DialogBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonMenu1.setOnClickListener{

        }

        binding.buttonMenu2.setOnClickListener{
            Toast.makeText(context, "Bottom Sheet 안의 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.buttonMenu3.setOnClickListener {
            Toast.makeText(context, "Bottom Sheet 안의 버튼 클릭", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}