package com.example.capstonedesign.retrofit

import com.example.capstonedesign.model.login.LoginPost
import com.example.capstonedesign.model.login.LoginResponse
import com.example.capstonedesign.model.login.SignupPost
import com.example.capstonedesign.model.login.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    // 회원가입
    @POST("auth/sign-up")
    suspend fun signup(
        @Body signupPost: SignupPost
    ): Response<SignupResponse>

    // 로그인
    @POST("auth/log-in")
    suspend fun login(
        @Body loginPost: LoginPost
    ): Response<LoginResponse>

}