package com.example.capstonedesign.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstonedesign.model.login.LoginPost
import com.example.capstonedesign.model.login.SignupPost
import com.example.capstonedesign.retrofit.RetrofitInstance.service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val signupResult = MutableLiveData<Int>()
    val loginResult = MutableLiveData<Int>()

    // 회원가입
    fun signup(signupPost: SignupPost) = CoroutineScope(Dispatchers.IO).launch {
            val response = service.signup(signupPost)
            signupResult.postValue(response.body()?.code)
    }

    // 로그인
    fun login(loginPost: LoginPost) = viewModelScope.launch {
        val response = service.login(loginPost)
        loginResult.postValue(response.body()?.code)
    }
}