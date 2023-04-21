package com.example.capstonedesign.repository

import com.example.capstonedesign.model.BasicResponse
import com.example.capstonedesign.model.board.AllCommentResponse
import com.example.capstonedesign.model.board.AllPostResponse
import com.example.capstonedesign.model.board.PostDetailInfoResponse
import com.example.capstonedesign.model.board.WriteCommentRequest
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
    suspend fun getAllPost(tag: String) : Response<AllPostResponse> {
        return service.getAllPost(tag)
    }

    // 게시글 상세 조회
    suspend fun getPostDetailInfo(boardId: Long) : Response<PostDetailInfoResponse> {
        return service.getPostDetailInfo(boardId)
    }

    // 게시글 수정
    suspend fun updatePost(boardId: Long) : Response<AllCommentResponse> {
        return service.getAllComments(boardId)
    }

    // 게시글 삭제
    suspend fun deletePost(boardId: Long) : Response<BasicResponse> {
        return service.deletePost("Bearer $ACCESS_TOKEN", boardId)
    }

    // 게시글별 댓글 조회
    suspend fun getAllComments(boardId: Long) : Response<AllCommentResponse> {
        return service.getAllComments(boardId)
    }

    // 댓글 작성
    suspend fun writeComments(boardId: Long, content: String) : Response<BasicResponse> {
        return service.writeComments("Bearer $ACCESS_TOKEN", boardId, WriteCommentRequest(content))
    }

    // 댓글 삭제
    suspend fun deleteComment(boardId: Long, commentId: Long) : Response<BasicResponse> {
        return service.deleteComment("Bearer $ACCESS_TOKEN", boardId, commentId)
    }
}