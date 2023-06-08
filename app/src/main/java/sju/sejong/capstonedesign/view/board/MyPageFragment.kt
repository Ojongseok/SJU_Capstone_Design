package sju.sejong.capstonedesign.view.board

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sju.sejong.capstonedesign.R
import sju.sejong.capstonedesign.databinding.DialogLogoutBinding
import sju.sejong.capstonedesign.databinding.FragmentMyPageBinding
import sju.sejong.capstonedesign.util.Constants.ACCESS_TOKEN
import sju.sejong.capstonedesign.util.Constants.LOGIN_STATUS
import sju.sejong.capstonedesign.util.Constants.MEMBER_ID
import sju.sejong.capstonedesign.viewmodel.LoginViewModel
import sju.sejong.capstonedesign.databinding.DialogNicknameModifyBinding
import sju.sejong.capstonedesign.dialog.LoginDialog
import sju.sejong.capstonedesign.dialog.LogoutDialog
import sju.sejong.capstonedesign.model.login.ModifyUserInfo
import sju.sejong.capstonedesign.view.home.HomeFragmentDirections
import java.util.regex.Pattern

@AndroidEntryPoint
class MyPageFragment: Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDataSettings()
        setObserver()

        binding.btnMypageModifyNickname.setOnClickListener {
            setModifyNicknameDialog()
        }
    }

    private fun setModifyNicknameDialog() {
        val dialog = Dialog(requireContext())
        val binding = DialogNicknameModifyBinding.inflate(LayoutInflater.from(requireContext()))

        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        binding.btnDialogModifyComplete.setOnClickListener {
            val newNickname = binding.etNicknameModify.text.toString()

            val nicknamePattern = "^[a-zA-Z0-9가-힣]{4,12}$"
            val pattern = Pattern.matches(nicknamePattern, newNickname)

            if (pattern) {
                viewModel.modifyNickname(ModifyUserInfo(newNickname), MEMBER_ID)
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "닉네임을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDialogModifyClose.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setObserver() {
        viewModel.memberInfo.observe(viewLifecycleOwner) {
            binding.model = it
        }
        viewModel.modifyNicknameState.observe(viewLifecycleOwner) {
            if (it == 200) {
                Toast.makeText(requireContext(), "닉네임 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            } else if (it == 706) {
                Toast.makeText(requireContext(), "이미 사용중인 닉네임입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initDataSettings() {
        binding.fragment = this@MyPageFragment

        viewModel.getMemberInfo(MEMBER_ID)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_back -> {
                findNavController().navigateUp()
            }
            R.id.btn_mypage_terms -> {
                val action = MyPageFragmentDirections.actionFragmentMypageToFragmentTerms()
                findNavController().navigate(action)
            }
            R.id.btn_mypage_logout -> {
                val logoutDialog = LogoutDialog(requireContext())
                logoutDialog.showLogoutDialog()

                logoutDialog.setItemClickListener(object : LogoutDialog.OnItemClickListener {
                    override fun onCompleteClick(v: View) {
                        viewModel.logout()
                        findNavController().navigateUp()

                        ACCESS_TOKEN = ""
                        LOGIN_STATUS = false

                        Toast.makeText(requireContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}