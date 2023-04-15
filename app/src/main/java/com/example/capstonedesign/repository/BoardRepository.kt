package com.example.capstonedesign.repository

import com.example.capstonedesign.model.BasicResponse
import com.example.capstonedesign.model.WritePostRequest
import com.example.capstonedesign.retrofit.RetrofitInstance.service
import com.example.capstonedesign.util.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class BoardRepository {
    val ACCESS_TOKEN = Constants.ACCESS_TOKEN

    // 게시글 작성
    suspend fun writePost(
        jsonBody: RequestBody, file: MultipartBody.Part?
    ): Response<BasicResponse> {
        return service.writePost("Bearer $ACCESS_TOKEN", jsonBody, file)
    }

    // 게시글 전체 조회
//    suspend fun getAllPost() : Response

    // 게시글 상세 조회
//    suspend fun getPstDetailInfo() : Response

}