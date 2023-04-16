package com.example.capstonedesign.retrofit

import com.example.capstonedesign.model.BasicResponse
import com.example.capstonedesign.model.board.AllPostResponse
import com.example.capstonedesign.model.login.LoginPost
import com.example.capstonedesign.model.login.LoginResponse
import com.example.capstonedesign.model.login.SignupPost
import com.example.capstonedesign.model.login.SignupResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

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


    // 게시글 작성
    @Multipart
    @POST("boards")
    suspend fun writePost(
        @Header ("Authorization") BearerToken: String,
        @Part ("request") writePostRequest: RequestBody,
        @Part file: MultipartBody.Part?
    ): Response<BasicResponse>

    // 게시글 전체 조회
    @GET("boards")
    suspend fun getAllPost(): Response<AllPostResponse>
}