package sju.sejong.capstonedesign.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sju.sejong.capstonedesign.repository.LoginRepository
import sju.sejong.capstonedesign.viewmodel.LoginViewModel

class LoginViewModelFactory(private val loginRepository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginRepository) as T
        }
        throw IllegalArgumentException("ViewModel class is not found")
    }
}