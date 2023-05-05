package sju.sejong.capstonedesign.model.login

data class MemberInfoResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: MemberInfoResult
)

data class MemberInfoResult(
    val memberId: Int,
    val email: String,
    val nickname: String,
    val region: String
)
