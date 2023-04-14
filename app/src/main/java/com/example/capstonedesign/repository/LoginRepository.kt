package com.example.capstonedesign.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.capstonedesign.model.login.LoginPost
import com.example.capstonedesign.model.login.LoginResponse
import com.example.capstonedesign.model.login.SignupPost
import com.example.capstonedesign.model.login.SignupResponse
import com.example.capstonedesign.retrofit.RetrofitInstance.service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response

private val Context.dataStore by preferencesDataStore(name = "dataStore")

class LoginRepository(private val context: Context) {

    // 회원가입
    suspend fun signup(signupPost: SignupPost) : Response<SignupResponse> {
        return service.signup(signupPost)
    }

    // 로그인
    suspend fun login(loginPost: LoginPost) : Response<LoginResponse> {
        return service.login(loginPost)
    }

    // dataStore에 토큰 쓰기
    suspend fun setLoginKey(accessToken: String) {
        context.dataStore.edit {
            it[loginKey] = accessToken
        }
    }

    // DataStore 엑세스토큰 읽기
    fun getAccessToken(): String {
        var token = ""
        val job = CoroutineScope(Dispatchers.IO).launch {
            token = loginStatus.first()
        }
        runBlocking {
            job.join()
        }

        return token
    }

    // DataStore
    private val loginKey = stringPreferencesKey("login_status")

    private val loginStatus : Flow<String> = context.dataStore.data
        .map {preferences ->
            preferences[loginKey] ?: ""
        }
}