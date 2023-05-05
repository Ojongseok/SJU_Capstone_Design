package sju.sejong.capstonedesign.repository

import sju.sejong.capstonedesign.model.BasicResponse
import sju.sejong.capstonedesign.model.board.*
import sju.sejong.capstonedesign.retrofit.RetrofitInstance.service
import sju.sejong.capstonedesign.util.Constants
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

    // 게시글 좋아요
    suspend fun postLike(boardId: Long) : Response<BasicResponse> {
        return service.postLike("Bearer $ACCESS_TOKEN", boardId)
    }

    // 게시글 좋아요 취소
    suspend fun postLikeCancel(boardId: Long) : Response<BasicResponse> {
        return service.postLikeCancel("Bearer $ACCESS_TOKEN", boardId)
    }

    // 댓글 작성
    suspend fun writeComments(boardId: Long, content: String, parentId: Long?) : Response<BasicResponse> {
        return service.writeComments("Bearer $ACCESS_TOKEN", boardId, WriteCommentRequest(content, parentId))
    }

    // 댓글 수정
    suspend fun modifyComment(boardId: Long, commentId: Long, content: String) : Response<BasicResponse> {
        return service.modifyComment("Bearer $ACCESS_TOKEN", boardId, commentId, WriteCommentRequest(content, null))
    }

    // 댓글 삭제
    suspend fun deleteComment(boardId: Long, commentId: Long) : Response<BasicResponse> {
        return service.deleteComment("Bearer $ACCESS_TOKEN", boardId, commentId)
    }

    // 게시글 해결완료
    suspend fun postSolve(boardId: Long, solveRequest: SolveRequest) : Response<BasicResponse> {
        return service.postSolve("Bearer $ACCESS_TOKEN", boardId, solveRequest)
    }
}