package com.example.capstonedesign.view.board

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.FragmentMyPageBinding
import com.example.capstonedesign.repository.LoginRepository
import com.example.capstonedesign.util.Constants.ACCESS_TOKEN
import com.example.capstonedesign.util.Constants.LOGIN_STATUS
import com.example.capstonedesign.util.Constants.MEMBER_ID
import com.example.capstonedesign.viewmodel.LoginViewModel
import com.example.capstonedesign.viewmodel.factory.LoginViewModelFactory
import kotlinx.android.synthetic.main.dialog_logout.*

class MyPageFragment: Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = LoginRepository(requireContext())
        val factory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        initDataSettings()
        setObserver()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnMypageLogout.setOnClickListener {
            setLogoutDialog()
        }
    }

    private fun setObserver() {
        viewModel.memberInfo.observe(viewLifecycleOwner) {
            Log.d("tag", it.result.toString())
        }
    }

    private fun initDataSettings() {
        viewModel.getMemberInfo(MEMBER_ID)
    }

    private fun setLogoutDialog() {
        val logoutDialog = Dialog(requireContext())

        logoutDialog.setContentView(R.layout.dialog_logout)
        logoutDialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        logoutDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        logoutDialog.setCanceledOnTouchOutside(false)
        logoutDialog.show()

        logoutDialog.dialog_logout_complete.setOnClickListener {
            viewModel.logout()
            logoutDialog.dismiss()
            Toast.makeText(requireContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            ACCESS_TOKEN = ""
            LOGIN_STATUS = false

            findNavController().navigateUp()
        }

        logoutDialog.dialog_logout_cancel.setOnClickListener {
            logoutDialog.dismiss()
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}