package com.example.capstonedesign.model.board

data class AllCommentResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<AllCommentResult>
)

data class AllCommentResult(
    val commentId: Long,
    val content: String,
    val memberId: Long,
    val nickname: String,
    val createdDate: String
)
