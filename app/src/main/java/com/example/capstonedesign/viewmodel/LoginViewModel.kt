package com.example.capstonedesign.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstonedesign.model.login.LoginPost
import com.example.capstonedesign.model.login.MemberInfoResponse
import com.example.capstonedesign.model.login.SignupPost
import com.example.capstonedesign.repository.LoginRepository
import com.example.capstonedesign.util.Constants.ACCESS_TOKEN
import com.example.capstonedesign.util.Constants.LOGIN_STATUS
import com.example.capstonedesign.util.Constants.MEMBER_ID
import kotlinx.coroutines.*

class LoginViewModel(
    private val repository: LoginRepository
): ViewModel() {
    val signupResult = MutableLiveData<Int>()
    val loginResult = MutableLiveData<Int>()
    val memberInfo = MutableLiveData<MemberInfoResponse>()

    // 회원가입
    fun signup(signupPost: SignupPost) = CoroutineScope(Dispatchers.IO).launch {
        val response = repository.signup(signupPost)
        signupResult.postValue(response.body()?.code)
    }

    // 로그인
    fun login(loginPost: LoginPost) = viewModelScope.launch {
        val response = repository.login(loginPost)

        if (response.body()?.code == 200) {
            repository.setLoginKey(response.body()?.result?.accessToken!!, response.body()?.result?.memberId!!)
            ACCESS_TOKEN = response.body()?.result?.accessToken!!
            LOGIN_STATUS = true
            MEMBER_ID = response.body()?.result?.memberId!!
        } else {
            repository.setLoginKey("", 0)
            ACCESS_TOKEN = ""
            LOGIN_STATUS = false
            MEMBER_ID = 0
        }

        loginResult.postValue(response.body()?.code)
    }

    // 로그아웃
    fun logout() = viewModelScope.launch {
        repository.setLoginKey("", 0)
        ACCESS_TOKEN = ""
        LOGIN_STATUS = false
        MEMBER_ID = 0
    }

    // 회원정보 조회
    fun getMemberInfo(memberId: Long) = viewModelScope.launch {
        val response = repository.getMemberInfo(memberId)
        memberInfo.postValue(response.body())
    }

    // DataStore 엑세스토큰 읽기
    fun getAccessToken(): String {
        return repository.getAccessToken()
    }

    fun getMemberId(): Long {
        return repository.getMemberId()
    }
}