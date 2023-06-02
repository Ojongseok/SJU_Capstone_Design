package sju.sejong.capstonedesign.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import sju.sejong.capstonedesign.databinding.DialogBottomSheetBinding
import sju.sejong.capstonedesign.util.Constants.LOGIN_STATUS
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import sju.sejong.capstonedesign.R
import sju.sejong.capstonedesign.databinding.DialogLoginBinding

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
            val action = BottomSheetDialogDirections.actionDialogBottomSheetToFragmentPesticideSearch()
            findNavController().navigate(action)
        }

        binding.buttonMenu5.setOnClickListener {
            dismiss()
        }
    }

    private fun setLoginDialog() {
        val loginDialog = Dialog(requireContext())
        val binding = DialogLoginBinding.inflate(LayoutInflater.from(requireContext()))

        loginDialog.setContentView(binding.root)
        loginDialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        loginDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loginDialog.setCanceledOnTouchOutside(false)
        loginDialog.show()

        binding.btnDialogLogin.setOnClickListener {
            loginDialog.dismiss()

            val action = BottomSheetDialogDirections.actionDialogBottomSheetToFragmentLogin()
            findNavController().navigate(action)
        }

        binding.btnDialogLoginClose.setOnClickListener {
            loginDialog.dismiss()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}