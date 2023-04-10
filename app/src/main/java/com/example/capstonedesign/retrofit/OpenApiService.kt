package com.example.capstonedesign.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstonedesign.model.CropDetailResponse
import com.example.capstonedesign.model.DiseaseDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenApiService {
    // 작물명으로 병해 목록 요청
    @GET("npmsAPI/service")
    suspend fun searchDetailCropInfo (
        @Query("apiKey") apiKey: String,
        @Query("serviceCode") serviceCode: String,
        @Query("serviceType") serviceType: String,
        @Query("cropName") cropName: String,
        @Query("displayCount") displayCount: Int = 50
    ) : CropDetailResponse

    // 병해명으로 병해 목록 요청
    @GET("npmsAPI/service")
    suspend fun searchDiseaseName (
        @Query("apiKey") apiKey: String,
        @Query("serviceCode") serviceCode: String,
        @Query("serviceType") serviceType: String,
        @Query("sickNameKor") sickNameKor: String,
        @Query("displayCount") displayCount: Int = 50
    ) : CropDetailResponse

    // 병해 상세정보 요청
    @GET("npmsAPI/service")
    suspend fun searchDiseaseDetailInfo (
        @Query("apiKey") apiKey: String,
        @Query("serviceCode") serviceCode: String,
        @Query("sickKey") sickKey: String
    ) : DiseaseDetailResponse

    // 작물병과 병해명으로 병해 상세정보 검색
    @GET("npmsAPI/service")
    suspend fun searchDiseaseHome (
        @Query("apiKey") apiKey: String,
        @Query("serviceCode") serviceCode: String,
        @Query("serviceType") serviceType: String,
        @Query("cropName") cropName: String,
        @Query("sickNameKor") sickNameKor: String
    ) : CropDetailResponse
}