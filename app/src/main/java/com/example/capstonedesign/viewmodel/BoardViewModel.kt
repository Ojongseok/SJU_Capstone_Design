package com.example.capstonedesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstonedesign.model.board.AllPostResponse
import com.example.capstonedesign.model.board.PostDetailInfoResponse
import com.example.capstonedesign.repository.BoardRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class BoardViewModel(private val repository: BoardRepository): ViewModel() {
    val writePostResultCode = MutableLiveData<Int>()    // 게시글 작성 결과
    val allPost = MutableLiveData<AllPostResponse>()    // 게시글 전체 조회
    val postDetailInfoResponse = MutableLiveData<PostDetailInfoResponse>()    // 게시글 상제 정보

    // 게시글 작성
    fun writePost(jsonBody: RequestBody, file: MultipartBody.Part?) = viewModelScope.launch {
        val response = repository.writePost(jsonBody, file)
        writePostResultCode.postValue(response.body()?.code)
    }

    // 게시글 전체 조회
    fun getAllPost() = viewModelScope.launch {
        val response = repository.getAllPost()
        allPost.postValue(response.body())
    }

    // 게시글 상세 조회
}