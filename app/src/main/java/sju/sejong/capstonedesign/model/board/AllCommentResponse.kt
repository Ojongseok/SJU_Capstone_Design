package sju.sejong.capstonedesign.model.board

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
    val createdDate: String,
    val childComments: List<ReCommentList>
)

data class ReCommentList(
    val commentId: Long,
    val parentId: Long,
    val content: String,
    val memberId: Long,
    val nickname: String,
    val createdDate: String
)
