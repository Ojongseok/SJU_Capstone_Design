package com.example.capstonedesign.view.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import java.util.regex.Pattern

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

        }

        checkValidation()

        setRegionMenu()
        setObserver()

    }

    private fun checkValidation() {
        binding.etSignupEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEmail()
            }
        })

        binding.etSignupPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPassword()
            }
        })

        binding.etSignupPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPasswordConfirm()
            }
        })

        val nickname = binding.etSignupNickname.text.toString()
        val region = binding.autoCompleteTextView.text.toString()

//        viewModel.signup(SignupPost(email, password, nickname, region))

    }

    private fun checkEmail(): Boolean {
        val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.matches(emailValidation, binding.etSignupEmail.text.toString())

        return if (pattern) {
            binding.tilSignupEmail.helperText = null
            binding.tilSignupEmail.boxStrokeColor = resources.getColor(R.color.main_green)
            true
        } else {
            binding.tilSignupEmail.helperText = "올바른 이메일 형식을 입력해주세요."
            binding.tilSignupEmail.boxStrokeColor = resources.getColor(R.color.main_red)
            false
        }
    }

    private fun checkPassword(): Boolean {
        val passwordValidation = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,12}$"
        val pattern = Pattern.matches(passwordValidation, binding.etSignupPassword.text.toString())

        return if (pattern) {
            binding.tilSignupPassword.helperText = null
            binding.tilSignupPassword.boxStrokeColor = resources.getColor(R.color.main_green)
            true
        } else {
            binding.tilSignupPassword.helperText = "비밀번호는 한글, 영문 포함 8~12자 이내입니다."
            binding.tilSignupPassword.boxStrokeColor = resources.getColor(R.color.main_red)
            false
        }
    }

    private fun checkPasswordConfirm(): Boolean {
        val password = binding.etSignupPassword.text.toString()
        val passwordConfirm = binding.etSignupPasswordConfirm.text.toString()

        return if (password == passwordConfirm) {
            binding.tilSignupPasswordConfirm.helperText = null
            binding.tilSignupPasswordConfirm.boxStrokeColor = resources.getColor(R.color.main_green)
            true
        } else {
            binding.tilSignupPasswordConfirm.helperText = "비밀번호가 일치하지 않습니다."
            binding.tilSignupPasswordConfirm.boxStrokeColor = resources.getColor(R.color.main_red)
            false
        }
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