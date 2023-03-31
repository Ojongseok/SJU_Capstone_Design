package com.example.capstonedesign.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenApiService {
    @GET("npmsAPI/service")
    suspend fun searchDisease (
        @Query("apiKey") apiKey: String,
        @Query("serviceCode") serviceCode: String,
        @Query("serviceType") serviceType: String,
        @Query("cropName") cropName: String
    ) : Response<SearchDiseaseResponse>

}