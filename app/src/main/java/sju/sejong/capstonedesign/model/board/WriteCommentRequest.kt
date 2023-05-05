package sju.sejong.capstonedesign.model.board

data class WriteCommentRequest(
    val content: String,
    val parentId: Long?
)
