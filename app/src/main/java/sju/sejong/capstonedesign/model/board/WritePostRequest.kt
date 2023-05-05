package sju.sejong.capstonedesign.model.board

data class WritePostRequest(
    val title: String,
    val content: String,
    val tag: String
)
