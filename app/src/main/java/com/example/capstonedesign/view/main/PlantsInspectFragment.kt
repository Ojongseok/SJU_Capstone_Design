package com.example.capstonedesign.view.main

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.FragmentPlantsInspectBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PlantsInspectFragment: Fragment() {
    private var _binding: FragmentPlantsInspectBinding? = null
    private val binding get() = _binding!!
    private var selectedPlants = 0
    private val PICK_FROM_CAMERA = 1
    private val PICK_FROM_GALLERY = 2
    private val REQUEST_IMAGE_CAPTURE = 3
    private lateinit var cropActivityResultLauncher : ActivityResultLauncher<Any?>

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
                Toast.makeText(requireContext(), "접근 권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
            } else {
                cropActivityResultLauncher.launch(null)
            }
        }
    }
//    private val permissionList2 = arrayOf(Manifest.permission.CAMERA)
//    private val checkPermission2 = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
//        result.forEach {
//            if(!it.value) {
//                Toast.makeText(requireContext(), "접근 권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
//            } else {
////                sendTakePhotoIntent()
//                dispatchTakePictureIntent()
//            }
//        }
//    }
//    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
//        override fun createIntent(context: Context, input: Any?): Intent {
//            return CropImage.activity()
//                .setAspectRatio(1,1)
//                .getIntent(requireActivity())
//        }
//
//        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
//            return CropImage.getActivityResult(intent)?.uri
//        }
//    }

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

//        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract) {
//            it?.let {
//                binding.ivInspectSelectImg.setImageURI(it)
//            }
//        }
    }

    fun openImagePickOption() {
        val items = arrayOf<String>("사진 촬영", "앨범에서 선택", "취소")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("사진 선택")
        builder.setItems(items) { dialog, position ->
            if (items[position] == "사진 촬영") {
//                checkPermission2.launch(permissionList2)
                dispatchTakePictureIntent()
            } else if (items[position] == "앨범에서 선택") {
                checkPermission.launch(permissionList)
            } else if (items[position] == "취소") {
                dialog.dismiss()
            }
        }
        builder.show()
    }
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            if (takePictureIntent.resolveActivity(this.requireContext().packageManager) != null) {
                // 찍은 사진을 그림파일로 만들기
                val photoFile: File? =
                    try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.d("TAG", "그림파일 만드는도중 에러생김")
                        null
                    }

                if (Build.VERSION.SDK_INT < 24) {
                    if(photoFile != null){
                        val photoURI = Uri.fromFile(photoFile)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    }
                }
                else{
                    // 그림파일을 성공적으로 만들었다면 startActivityForResult로 보내기
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(requireContext(), requireContext().packageName, photoFile)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    }
                }

                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra("crop", true)
                intent.action = Intent.ACTION_GET_CONTENT

                val chooserIntent = Intent.createChooser(intent, "저장 공간을 선택해주세요.")
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePictureIntent))
                startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            3 -> {
                if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                    // 카메라로부터 받은 데이터가 있을경우에만
                    val file = File(imageFilePath)
                    val selectedUri = Uri.fromFile(file)
                    try{
                        selectedUri?.let {
                            if (Build.VERSION.SDK_INT < 28) {
                                launchImageCrop(selectedUri)
                            }
                            else{
                                launchImageCrop(selectedUri)
                            }
                        }

                    }catch (e: java.lang.Exception){
                        e.printStackTrace()
                    }
                }
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                Log.d("tag",requestCode.toString())
                val result = CropImage.getActivityResult(data)
                if(resultCode == RESULT_OK){
                    result.uri?.let {
                        binding.ivInspectSelectImg.setImageURI(result.uri)
                    }
                } else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    val error = result.error
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun launchImageCrop(uri: Uri?){
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .setAspectRatio(1,1)
            .start(requireContext(), this)
    }

    private var imageFilePath: String? = null
    private var photoUri: Uri? = null
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "TEST_" + timeStamp + "_"
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image: File = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        imageFilePath = image.absolutePath
        return image
    }
//    private fun sendTakePhotoIntent() {
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
//            var photoFile: File? = null
//            try {
//                photoFile = createImageFile()
//            } catch (ex: IOException) {
//                // Error occurred while creating the File
//            }
//            if (photoFile != null) {
//                photoUri = FileProvider.getUriForFile(requireContext(), requireContext().packageName, photoFile)
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }
//    }

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