package com.example.capstonedesign.view.board

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
import androidx.navigation.fragment.findNavController
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.FragmentBoardBinding
import com.example.capstonedesign.util.Constants.LOGIN_STATUS
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.dialog_login.*

class BoardFragment: Fragment() {
    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTabLayout()

        binding.btnBoardMypage.setOnClickListener {
            if (LOGIN_STATUS) {
                val action = BoardFragmentDirections.actionFragmentBoardToFragmentMypage()
                findNavController().navigate(action)
            } else {
                setLoginDialog()
            }
        }

        binding.btnBoardSearch.setOnClickListener {
            Toast.makeText(requireContext(), "준비중입니다.", Toast.LENGTH_SHORT).show()
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

            val action = BoardFragmentDirections.actionFragmentBoardToFragmentLogin()
            findNavController().navigate(action)
        }

        loginDialog.btn_dialog_login_close.setOnClickListener {
            loginDialog.dismiss()
        }
    }

    private fun setTabLayout() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.board_frg_tab_layout_container, RequestBoardFragment()).commit()

        binding.boardFrgTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.board_frg_tab_layout_container, RequestBoardFragment()).commit()
                    }

                    1 -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.board_frg_tab_layout_container, TipBoardFragment()).commit()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}