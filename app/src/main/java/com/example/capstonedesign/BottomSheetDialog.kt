package com.example.capstonedesign

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.capstonedesign.databinding.DialogBottomSheetBinding
import com.example.capstonedesign.util.Constants.LOGIN_STATUS
import com.example.capstonedesign.view.board.BoardFragmentDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_login.*

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
            if (LOGIN_STATUS) {
                val action = BottomSheetDialogDirections.actionDialogBottomSheetToFragmentPostWrite()
                findNavController().navigate(action)
            } else {
                setLoginDialog()
            }
        }

        binding.buttonMenu3.setOnClickListener {
            val action = BottomSheetDialogDirections.actionDialogBottomSheetToFragmentSearchDisease()
            findNavController().navigate(action)
        }

        binding.buttonMenu4.setOnClickListener {
            Toast.makeText(requireContext(), "준비중입니다.", Toast.LENGTH_SHORT).show()
        }

        binding.buttonMenu5.setOnClickListener {
            dismiss()
        }
    }

    private fun setLoginDialog() {
        val loginDialog = Dialog(requireContext())

        loginDialog.setContentView(R.layout.dialog_login)
        loginDialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        loginDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loginDialog.setCanceledOnTouchOutside(false)
        loginDialog.show()

        loginDialog.btn_dialog_login.setOnClickListener {
            loginDialog.dismiss()

            val action = BottomSheetDialogDirections.actionDialogBottomSheetToFragmentLogin()
            findNavController().navigate(action)
        }

        loginDialog.btn_dialog_login_close.setOnClickListener {
            loginDialog.dismiss()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}