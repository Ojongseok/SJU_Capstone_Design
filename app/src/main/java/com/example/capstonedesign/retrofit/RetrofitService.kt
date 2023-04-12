package com.example.capstonedesign.retrofit

import retrofit2.http.POST

interface RetrofitService {
    // 회원가입
    @POST("")
    suspend fun signup(

    )

    // 로그인

}