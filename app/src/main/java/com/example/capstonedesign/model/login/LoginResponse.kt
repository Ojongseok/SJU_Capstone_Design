package com.example.capstonedesign.model.login

data class LoginResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)

data class Result(
    val accessToken: String,
    val grantType: String,
    val refreshToken: String
)