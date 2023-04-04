package com.example.capstonedesign.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstonedesign.model.CropDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenApiService {
    @GET("npmsAPI/service")
    suspend fun searchDetailCropInfo (
        @Query("apiKey") apiKey: String,
        @Query("serviceCode") serviceCode: String,
        @Query("serviceType") serviceType: String,
        @Query("cropName") cropName: String
    ) : CropDetailResponse

}