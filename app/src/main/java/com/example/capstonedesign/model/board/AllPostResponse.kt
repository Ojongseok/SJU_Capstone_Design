package com.example.capstonedesign.model.board

data class AllPostResponse(
    val content: List<ContentList>,
    val hasNext: Boolean,
    val first: Boolean,
    val last: Boolean
)

data class ContentList(
    val boardId: Long,
    val title: String,
    val content: String,
    val tag: String,
    val image: String,
    val isSolved: Boolean,
    val memberId: Long,
    val nickname: String,
    val likeNum: Int,
    val commentNum: Int,
    val createdDate: String
)