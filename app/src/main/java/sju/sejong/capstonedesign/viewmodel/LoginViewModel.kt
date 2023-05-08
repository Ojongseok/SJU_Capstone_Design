package sju.sejong.capstonedesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import sju.sejong.capstonedesign.model.login.LoginResponse
import sju.sejong.capstonedesign.model.login.MemberInfoResponse
import sju.sejong.capstonedesign.model.login.SignupPost
import sju.sejong.capstonedesign.repository.LoginRepository
import sju.sejong.capstonedesign.model.login.LoginPost
import sju.sejong.capstonedesign.util.Constants.ACCESS_TOKEN
import sju.sejong.capstonedesign.util.Constants.LOGIN_STATUS
import sju.sejong.capstonedesign.util.Constants.MEMBER_ID
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository): ViewModel() {
    val signupResult = MutableLiveData<Int>()
    val loginResult = MutableLiveData<LoginResponse>()
    val memberInfo = MutableLiveData<MemberInfoResponse>()
    val loginEnableState = MutableLiveData<Boolean>()

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

        loginResult.postValue(response.body())
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