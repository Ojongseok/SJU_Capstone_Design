package com.example.capstonedesign.retrofit

import com.example.capstonedesign.model.BasicResponse
import com.example.capstonedesign.model.board.AllPostResponse
import com.example.capstonedesign.model.board.PostDetailInfoResponse
import com.example.capstonedesign.model.board.WriteCommentRequest
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
    suspend fun getAllPost(
        @Query("tag") tag: String
    ): Response<AllPostResponse>

    // 게시글 상세 조회
    @GET("boards/{board_id}")
    suspend fun getPostDetailInfo(
        @Path("board_id") boardId: Long
    ): Response<PostDetailInfoResponse>

    // 게시글 수정
//    @PATCH("boards/{board_id}")
//    suspend fun updatePost(
//        @Header ("Authorization") BearerToken: String,
//        @Path("board_id") boardId: Long
//    ): Response<BasicResponse>

    // 게시글 삭제
    @DELETE("boards/{board_id}")
    suspend fun deletePost(
        @Header ("Authorization") BearerToken: String,
        @Path("board_id") boardId: Long
    ): Response<BasicResponse>

    // 게시글별 모든 댓글 조회
    @GET("boards/{board_id}/comments")
    suspend fun getComments(

    )

    // 댓글 작성
    @POST("boards/{board_id}/comments")
    suspend fun writeComments(
        @Header ("Authorization") BearerToken: String,
        @Path("board_id") boardId: Long,
        @Body writeCommentRequest: WriteCommentRequest
    ): Response<BasicResponse>
}