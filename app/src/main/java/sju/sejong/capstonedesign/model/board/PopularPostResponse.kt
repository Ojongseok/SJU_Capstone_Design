package sju.sejong.capstonedesign.model.board

data class PopularPostResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result : List<PopularPostList>
)

data class PopularPostList(
    val boardId: Long,
    val title: String,
    val content: String,
    val tag: String,
    val image: String?,
    val isSolved: Boolean,
    val memberId: Long,
    val nickname: String,
    val likeNum: Int,
    val commentNum: Int,
    val createdDate: String
)