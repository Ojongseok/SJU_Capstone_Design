package com.example.capstonedesign.view.main

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.FragmentPlantsInspectBinding


class PlantsInspectFragment: Fragment() {
    private var _binding: FragmentPlantsInspectBinding? = null
    private val binding get() = _binding!!
    private var selectedPlants = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPlantsInspectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        selectPlantsCategory()
        binding.ivInspectSelectImg.clipToOutline = true

        binding.ivInspectSelectImg.setOnClickListener {
            openImagePickOption()
        }
    }

    fun openImagePickOption() {
        val items = arrayOf<String>("사진 촬영", "앨범에서 선택", "취소")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("사진 선택")
        builder.setItems(items) { dialog, position ->
            if (items[position] == "사진 촬영") {
                Toast.makeText(requireContext(), items[position], Toast.LENGTH_SHORT).show()
            } else if (items[position] == "앨범에서 선택") {
                Toast.makeText(requireContext(), items[position], Toast.LENGTH_SHORT).show()
            } else if (items[position] == "취소") {
                dialog.dismiss()
            }
        }
        builder.show()
    }



    private fun selectPlantsCategory() {
        binding.btnInspectCategory1.setOnClickListener {
            selectedPlants = 1
            buttonColorSet(selectedPlants)
        }
        binding.btnInspectCategory2.setOnClickListener {
            selectedPlants = 2
            buttonColorSet(selectedPlants)
        }
        binding.btnInspectCategory3.setOnClickListener {
            selectedPlants = 3
            buttonColorSet(selectedPlants)
        }
        binding.btnInspectCategory4.setOnClickListener {
            selectedPlants = 4
            buttonColorSet(selectedPlants)
        }
    }

    private fun buttonColorSet(position: Int) {
        buttonClearSet()

        when(position) {
            1 -> {
                binding.btnInspectCategory1.setBackgroundResource(R.drawable.background_rec_10dp_green)
                binding.ivInspectSelectCategory1.visibility = View.VISIBLE
            }
            2 -> {
                binding.btnInspectCategory2.setBackgroundResource(R.drawable.background_rec_10dp_green)
                binding.ivInspectSelectCategory2.visibility = View.VISIBLE
            }
            3 -> {
                binding.btnInspectCategory3.setBackgroundResource(R.drawable.background_rec_10dp_green)
                binding.ivInspectSelectCategory3.visibility = View.VISIBLE
            }
            4 -> {
                binding.btnInspectCategory4.setBackgroundResource(R.drawable.background_rec_10dp_green)
                binding.ivInspectSelectCategory4.visibility = View.VISIBLE
            }
        }
    }

    private fun buttonClearSet() {
        binding.btnInspectCategory1.setBackgroundResource(R.drawable.background_rec_10dp_grey)
        binding.btnInspectCategory2.setBackgroundResource(R.drawable.background_rec_10dp_grey)
        binding.btnInspectCategory3.setBackgroundResource(R.drawable.background_rec_10dp_grey)
        binding.btnInspectCategory4.setBackgroundResource(R.drawable.background_rec_10dp_grey)

        binding.ivInspectSelectCategory1.visibility = View.INVISIBLE
        binding.ivInspectSelectCategory2.visibility = View.INVISIBLE
        binding.ivInspectSelectCategory3.visibility = View.INVISIBLE
        binding.ivInspectSelectCategory4.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}