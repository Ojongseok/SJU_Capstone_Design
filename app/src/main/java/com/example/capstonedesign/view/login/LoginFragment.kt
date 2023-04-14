package com.example.capstonedesign.view.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.capstonedesign.databinding.FragmentLoginBinding
import com.example.capstonedesign.model.login.LoginPost
import com.example.capstonedesign.repository.LoginRepository
import com.example.capstonedesign.viewmodel.LoginViewModel
import com.example.capstonedesign.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
//    private val viewModel: LoginViewModel by viewModels()
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = LoginRepository(requireContext())
        val factory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

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
            if (it == 200) {
                if (viewModel.getAccessToken().isNotEmpty()) {
                    Toast.makeText(requireContext(), "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    Log.d("tag","1")
                }
            } else {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}