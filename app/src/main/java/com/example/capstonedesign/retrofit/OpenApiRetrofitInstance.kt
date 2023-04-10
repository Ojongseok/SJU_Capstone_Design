package com.example.capstonedesign.retrofit

import com.example.capstonedesign.BuildConfig
import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenApiRetrofitInstance {
    const val API_KEY = BuildConfig.OPEN_API_KEY

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