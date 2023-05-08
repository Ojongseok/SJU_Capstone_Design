package sju.sejong.capstonedesign.view.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sju.sejong.capstonedesign.databinding.FragmentLoginBinding
import sju.sejong.capstonedesign.model.login.LoginPost
import sju.sejong.capstonedesign.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            viewModel.login(LoginPost(email, password))
        }

        binding.btnSignup.setOnClickListener {
            val action = LoginFragmentDirections.actionFragmentLoginToFragmentSignup()
            findNavController().navigate(action)
        }

        setObserver()
    }

    private fun setObserver() {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                if (viewModel.getAccessToken().isNotEmpty()) {
                    Toast.makeText(requireContext(), "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    Log.d("tag","토큰이 비어있어요.")
                }
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}