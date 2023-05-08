package sju.sejong.capstonedesign.view.board

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import sju.sejong.capstonedesign.R
import sju.sejong.capstonedesign.databinding.FragmentPostWriteBinding
import sju.sejong.capstonedesign.viewmodel.BoardViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
@AndroidEntryPoint
class PostWriteFragment: Fragment() {
    private var _binding: FragmentPostWriteBinding? = null
    private val binding get() = _binding!!
    private val REQUEST_IMAGE_CAPTURE = 3
    private var imageFilePath: String? = null
    private var selectBoardCategory = ""
    private var selectedImage: Bitmap? = null
    private val viewModel by viewModels<BoardViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPostWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnPostWriteCamera.setOnClickListener {
            openImagePickOption()
        }

        binding.rgPostWrite.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_post_write_1 -> selectBoardCategory = "QUESTION"
                R.id.rb_post_write_2 -> selectBoardCategory = "KNOWHOW"
            }
        }

        binding.btnPostWrite.setOnClickListener {
            val title = binding.etPostWriteTitle.text.toString()
            val content = binding.etPostWriteContents.text.toString()
            val jsonObject = JSONObject("{\"title\":\"${title}\",\"content\":\"${content}\",\"tag\":\"${selectBoardCategory}\"}").toString()
            val jsonBody = RequestBody.create("application/json".toMediaTypeOrNull(),jsonObject)

            val file = RequestBody.create(MultipartBody.FORM, "")
            val body: MultipartBody.Part? = MultipartBody.Part.createFormData("file","", file)

            val bitmapRequestBody = selectedImage?.let { BitmapRequestBody(it) }
            val bitmapMultipartBody: MultipartBody.Part? =
                if (bitmapRequestBody == null) null
                else MultipartBody.Part.createFormData("file", "file", bitmapRequestBody)

            if (selectBoardCategory == "") {
                Toast.makeText(requireContext(), "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                if (title.isNotEmpty()) {
                    if (content.isNotEmpty()) {
                        viewModel.writePost(jsonBody, bitmapMultipartBody)
                    } else {
                        Toast.makeText(requireContext(), "게시글 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "게시글 제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun String?.toPlainRequestBody() = requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())

    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
        }
    }

    private fun setObserver() {
        viewModel.writePostResultCode.observe(viewLifecycleOwner) {
            if (it == 200) {
                Toast.makeText(requireContext(), "게시글 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    private fun openImagePickOption() {
        val items = arrayOf<String>("카메라 촬영 / 갤러리 선택", "취소")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("업로드 방법 선택")
        builder.setItems(items) { dialog, position ->
            if (items[position] == "카메라 촬영 / 갤러리 선택") {
                checkPermission()
            } else if (items[position] == "취소") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun checkPermission() {
        val WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val READ_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
        val CAMERA_PERMISSION = Manifest.permission.CAMERA

        var writePermission = ContextCompat.checkSelfPermission(requireContext(), WRITE_PERMISSION)
        var readPermission = ContextCompat.checkSelfPermission(requireContext(), READ_PERMISSION)
        var cameraPermission = ContextCompat.checkSelfPermission(requireContext(), CAMERA_PERMISSION)

        //권한이 없는 경우
        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED
            || cameraPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(WRITE_PERMISSION, READ_PERMISSION, CAMERA_PERMISSION),
                1 //사용자 임의 상수 (1로 설정해줌)
            )
        }

        if (writePermission == PackageManager.PERMISSION_GRANTED || readPermission == PackageManager.PERMISSION_GRANTED
            || cameraPermission == PackageManager.PERMISSION_GRANTED) {
            //권한이 있는 경우 실행할 동작
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            if (takePictureIntent.resolveActivity(this.requireContext().packageManager) != null) {
                // 찍은 사진을 그림파일로 만들기
                val photoFile: File? = try {
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
                if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
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
                val result = CropImage.getActivityResult(data)

                if(resultCode == Activity.RESULT_OK){
                    selectedImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, result.uri))
                    } else {
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, result.uri)
                    }
                    result.uri?.let {
                        Log.d("tag", selectedImage.toString())
                        binding.ivPostWrite.setImageBitmap(selectedImage)
                    }
                } else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    val error = result.error
//                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
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
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}