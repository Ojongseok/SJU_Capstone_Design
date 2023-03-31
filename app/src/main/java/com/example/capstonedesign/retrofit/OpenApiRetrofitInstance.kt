package com.example.capstonedesign.retrofit

import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object OpenApiRetrofitInstance {
    const val API_KEY = "2023d617f87a47faecb47a7e73cca7c3fdc4"

    private val okHttpClient: OkHttpClient by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(TikXmlConverterFactory.create(parser))
            .client(okHttpClient)    // Logcat에서 패킷 내용을 로그로 남기는 속성
            .baseUrl("http://ncpms.rda.go.kr/")
            .build()
    }

    val OpenApiRetrofitService: OpenApiService by lazy {
        retrofit.create(OpenApiService::class.java)
    }
}