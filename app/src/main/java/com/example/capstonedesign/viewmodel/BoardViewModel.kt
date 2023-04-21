package com.example.capstonedesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstonedesign.model.board.AllCommentResponse
import com.example.capstonedesign.model.board.AllPostResponse
import com.example.capstonedesign.model.board.PostDetailInfoResponse
import com.example.capstonedesign.model.login.MemberInfoResponse
import com.example.capstonedesign.repository.BoardRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class BoardViewModel(private val repository: BoardRepository): ViewModel() {
    val PostListResponse = MutableLiveData<AllPostResponse>()    // 게시글 전체 조회
    val postDetailResponse = MutableLiveData<PostDetailInfoResponse>()    // 게시글 상세 조회
    val writePostResultCode = MutableLiveData<Int>()    // 게시글 작성 결과
    val postUpdateResultCode = MutableLiveData<Int>()    // 게시글 수정 결과
    val postDeleteResultCode = MutableLiveData<Int>()    // 게시글 삭제 결과
    val getAllCommentsResponse = MutableLiveData<AllCommentResponse>()    // 댓글 전체 조회
    val writeCommentsResultCode = MutableLiveData<Int>()    // 댓글 작성 결과
    val deleteCommentResultCode = MutableLiveData<Int>()    // 댓글 삭제 결과

    // 게시글 작성
    fun writePost(jsonBody: RequestBody, file: MultipartBody.Part?) = viewModelScope.launch {
        val response = repository.writePost(jsonBody, file)
        writePostResultCode.postValue(response.body()?.code)
    }

    // 게시글 전체 조회
    fun getAllPost(tag: String) = viewModelScope.launch {
        val response = repository.getAllPost(tag)
        PostListResponse.postValue(response.body())
    }

    // 게시글 상세 조회
    fun getPostDetailInfo(boardId: Long) = viewModelScope.launch {
        val response = repository.getPostDetailInfo(boardId)
        postDetailResponse.postValue(response.body())
    }

    // 게시글 수정
//    fun updatePost(boardId: Long) = viewModelScope.launch {
//        val response = repository.updatePost(boardId)
//        postUpdateResultCode.postValue(response.body()?.code)
//    }

    // 게시글 삭제
    fun deletePost(boardId: Long) = viewModelScope.launch {
        val response = repository.deletePost(boardId)
        postDeleteResultCode.postValue(response.body()?.code)
    }

    // 게시글별 댓글 조회
    fun getAllComments(boardId: Long) = viewModelScope.launch {
        val response = repository.getAllComments(boardId)
        getAllCommentsResponse.postValue(response.body())
    }

    // 댓글 작성
    fun writeComments(boardId: Long, content: String) = viewModelScope.launch {
        val response = repository.writeComments(boardId, content)
        writeCommentsResultCode.postValue(response.body()?.code)

        getAllComments(boardId)
    }

    // 댓글 삭제
    fun deleteComment(boardId: Long, commentId: Long) = viewModelScope.launch {
        val response = repository.deleteComment(boardId, commentId)
        deleteCommentResultCode.postValue(response.body()?.code)

        getAllComments(boardId)
    }
}