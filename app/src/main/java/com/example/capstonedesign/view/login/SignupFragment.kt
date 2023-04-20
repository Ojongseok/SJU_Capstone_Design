package com.example.capstonedesign.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.capstonedesign.R
import com.example.capstonedesign.databinding.FragmentSignupBinding
import com.example.capstonedesign.model.login.SignupPost
import com.example.capstonedesign.repository.LoginRepository
import com.example.capstonedesign.viewmodel.LoginViewModel
import com.example.capstonedesign.viewmodel.factory.LoginViewModelFactory

class SignupFragment: Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
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

        binding.btnSignupComplete.setOnClickListener {
            val email = binding.etSignupEmail.text.toString()
            val password = binding.etSignupPassword.text.toString()
            val nickname = binding.etSignupNickname.text.toString()
            val region = binding.autoCompleteTextView.text.toString()

            viewModel.signup(SignupPost(email, password, nickname, region))
        }

        setRegionMenu()
        setObserver()

    }

    private fun setObserver() {
        viewModel.signupResult.observe(viewLifecycleOwner) {
            if (it == 200) {
                Toast.makeText(requireContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    private fun setRegionMenu() {
        val regionArray = resources.getStringArray(R.array.signup_select_region)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, regionArray)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}