package com.example.capstonedesign.model.board

data class PostDetailInfoResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)

data class Result(
    val boardId: Long,
    val title: String,
    val content: String,
    val tag: String,
    val image: String,
    val isSolved: Boolean,
    val memberId: Long,
    val nickname: String,
    val likeMemberIds: List<Long>,
    val likeNum: Int,
    val modifiedDate: String
)