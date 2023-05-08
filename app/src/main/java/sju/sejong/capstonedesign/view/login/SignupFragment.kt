package sju.sejong.capstonedesign.view.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sju.sejong.capstonedesign.R
import sju.sejong.capstonedesign.databinding.FragmentSignupBinding
import sju.sejong.capstonedesign.model.login.SignupPost
import sju.sejong.capstonedesign.viewmodel.LoginViewModel
import java.util.regex.Pattern

@AndroidEntryPoint
class SignupFragment: Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ltSignupTerms.setOnClickListener {
            val action = SignupFragmentDirections.actionFragmentSignupToFragmentTerms()
            findNavController().navigate(action)
        }

        binding.btnSignupComplete.setOnClickListener {
            if (checkEmail() && checkPassword() && checkPasswordConfirm() && checkNickname()
                && resources.getStringArray(R.array.signup_select_region).contains(binding.autoCompleteTextView.text.toString())) {
                viewModel.signup(SignupPost(
                    binding.etSignupEmail.text.toString(),
                    binding.etSignupPassword.text.toString(),
                    binding.etSignupNickname.text.toString(),
                    binding.autoCompleteTextView.text.toString()
                ))
            } else {
                if (binding.autoCompleteTextView.text.toString() == "지역을 선택해주세요.") {
                    Toast.makeText(requireContext(), "지역을 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.cbSignup.setOnCheckedChangeListener { buttonView, isChecked ->
            checkLoginEnable()
        }

        setRegionMenu()
        setObserver()
        checkValidation()
    }

    private fun checkValidation() {
        binding.etSignupEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEmail()
                checkLoginEnable()
            }
        })

        binding.etSignupPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPassword()
                checkLoginEnable()
            }
        })

        binding.etSignupPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPasswordConfirm()
                checkLoginEnable()
            }
        })

        binding.etSignupNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkNickname()
                checkLoginEnable()
            }
        })

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

    private fun checkNickname(): Boolean {
        val nicknamePattern = "^[a-zA-Z0-9가-힣]{4,12}$"
        val pattern = Pattern.matches(nicknamePattern, binding.etSignupNickname.text.toString())

        return if (pattern) {
            binding.tilSignupNickname.helperText = null
            binding.tilSignupNickname.boxStrokeColor = resources.getColor(R.color.main_green)
            true
        } else {
            binding.tilSignupNickname.helperText = "닉네임은 한글, 영문, 숫자 포함 4~12자 이내입니다."
            binding.tilSignupNickname.boxStrokeColor = resources.getColor(R.color.main_red)
            false
        }
    }

    private fun checkLoginEnable() {
        viewModel.loginEnableState.value = (checkEmail() && checkPassword() && checkPasswordConfirm() && checkNickname()
                && resources.getStringArray(R.array.signup_select_region).contains(binding.autoCompleteTextView.text.toString())
                && binding.cbSignup.isChecked)
    }

    private fun setObserver() {
        viewModel.signupResult.observe(viewLifecycleOwner) {
            if (it == 200) {
                Toast.makeText(requireContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.loginEnableState.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnSignupComplete.setBackgroundColor(resources.getColor(R.color.main_green))
            } else {
                binding.btnSignupComplete.setBackgroundColor(resources.getColor(R.color.sub_text))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setRegionMenu()
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