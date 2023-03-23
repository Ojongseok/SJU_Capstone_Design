package com.example.capstonedesign.view.main

import android.Manifest
import android.R.attr
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.FragmentPlantsInspectBinding


class PlantsInspectFragment: Fragment() {
    private var _binding: FragmentPlantsInspectBinding? = null
    private val binding get() = _binding!!
    private var selectedPlants = 0
    private val PICK_FROM_CAMERA = 1
    private val PICK_FROM_GALLERY = 2

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
                Toast.makeText(requireContext(), "접근 권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
            } else {
                pickImage()
            }
        }
    }
    private val permissionList2 = arrayOf(Manifest.permission.CAMERA)
    private val checkPermission2 = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
                Toast.makeText(requireContext(), "접근 권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
            } else {
                callCamera()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPlantsInspectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivInspectSelectImg.clipToOutline = true
        selectPlantsCategory()

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
                checkPermission2.launch(permissionList2)
            } else if (items[position] == "앨범에서 선택") {
                checkPermission.launch(permissionList)
            } else if (items[position] == "취소") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === PICK_FROM_CAMERA) {
            val extras: Bundle? = data?.extras
            if (extras != null) {
                val photo = extras.getParcelable<Bitmap>("data")
                binding.ivInspectSelectImg.setImageBitmap(photo) // [이미지 뷰에 표시]
            } else {

            }
        }

        if (requestCode === PICK_FROM_GALLERY) {
            val extras2: Bundle? = data?.extras
            if (extras2 != null) {
                val photo = extras2.getParcelable<Bitmap>("data")
                binding.ivInspectSelectImg.setImageBitmap(photo) // [이미지 뷰에 표시]
            } else {
                Toast.makeText(requireContext(), "취소되었습니다.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun callCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString())

        // [이미지 잘라내기 위한 크기]
        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 0)
        intent.putExtra("aspectY", 0)
        intent.putExtra("outputX", 200)
        intent.putExtra("outputY", 150)

        try {
            intent.putExtra("return-data", true)
            startActivityForResult(intent, PICK_FROM_CAMERA)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = "image/*" // [이미지 타입 지정]
        intent.action = Intent.ACTION_PICK

        // [잘라내기 셋팅]
        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 0)
        intent.putExtra("aspectY", 0)
        intent.putExtra("outputX", 200)
        intent.putExtra("outputY", 150)
        try {
            intent.putExtra("return-data", true)
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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