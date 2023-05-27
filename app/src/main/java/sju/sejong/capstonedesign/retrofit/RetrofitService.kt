package sju.sejong.capstonedesign.retrofit

import sju.sejong.capstonedesign.model.BasicResponse
import sju.sejong.capstonedesign.model.board.*
import sju.sejong.capstonedesign.model.login.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import sju.sejong.capstonedesign.model.InspectResponse

interface RetrofitService {
    // 회원가입
    @POST("auth/sign-up")
    suspend fun signup(
        @Body signupPost: SignupPost
    ): Response<SignupResponse>

    // 로그인
    @POST("auth/log-in")
    suspend fun login(
        @Body loginPost: LoginPost
    ): Response<LoginResponse>

    // 회원정보 조회
    @GET("members/{member_id}")
    suspend fun getMemberInfo(
        @Header ("Authorization") BearerToken: String,
        @Path("member_id") memberId: Long
    ): Response<MemberInfoResponse>

    // 닉네임 변경
    @PATCH("members/{member_id}")
    suspend fun modifyNickname(
        @Header ("Authorization") BearerToken: String,
        @Body modifyUserInfo: ModifyUserInfo,
        @Path("member_id") memberId: Long
    ) : Response<BasicResponse>

    // 지역별 병해 빈도
    @GET("diagnosis-result/monthly")
    suspend fun getAllRegionDisease(): Response<AllRegionDiseaseResponse>

    // 병해 진단
    @Multipart
    @POST("diagnosis")
    suspend fun startDiagnosis(
        @Part ("request") diagnosisRequest: RequestBody,
        @Part file: MultipartBody.Part?
    ) : Response<InspectResponse>

    // 게시글 작성
    @Multipart
    @POST("boards")
    suspend fun writePost(
        @Header ("Authorization") BearerToken: String,
        @Part ("request") writePostRequest: RequestBody,
        @Part file: MultipartBody.Part?
    ): Response<BasicResponse>

    // 게시글 전체 조회
    @GET("boards")
    suspend fun getAllPost(
        @Query("tag") tag: String,
        @Query("size") size: String = "50"
    ): Response<AllPostResponse>

    // 게시글 상세 조회
    @GET("boards/{board_id}")
    suspend fun getPostDetailInfo(
        @Path("board_id") boardId: Long
    ): Response<PostDetailInfoResponse>

    // 게시글 수정
//    @PATCH("boards/{board_id}")
//    suspend fun updatePost(
//        @Header ("Authorization") BearerToken: String,
//        @Path("board_id") boardId: Long
//    ): Response<BasicResponse>

    // 게시글 삭제
    @DELETE("boards/{board_id}")
    suspend fun deletePost(
        @Header ("Authorization") BearerToken: String,
        @Path("board_id") boardId: Long
    ): Response<BasicResponse>

    // 게시글별 모든 댓글 조회
    @GET("boards/{board_id}/comments")
    suspend fun getAllComments(
        @Path("board_id") boardId: Long
    ): Response<AllCommentResponse>

    // 게시글 좋아요
    @POST("boards/{board_id}/like")
    suspend fun postLike(
        @Header ("Authorization") BearerToken: String,
        @Path("board_id") boardId: Long
    ): Response<BasicResponse>

    // 게시글 좋아요 취소
    @DELETE("boards/{board_id}/like")
    suspend fun postLikeCancel(
        @Header ("Authorization") BearerToken: String,
        @Path("board_id") boardId: Long
    ): Response<BasicResponse>

    // 댓글 작성
    @POST("boards/{board_id}/comments")
    suspend fun writeComments(
        @Header ("Authorization") BearerToken: String,
        @Path("board_id") boardId: Long,
        @Body writeCommentRequest: WriteCommentRequest
    ): Response<BasicResponse>

    // 댓글 수정
    @PATCH("boards/{board_id}/comments/{comment_id}")
    suspend fun modifyComment(
        @Header ("Authorization") BearerToken: String,
        @Path("board_id") boardId: Long,
        @Path("comment_id") comment_id: Long,
        @Body writeCommentRequest: WriteCommentRequest
    ): Response<BasicResponse>

    // 댓글 삭제
    @DELETE("boards/{board_id}/comments/{comment_id}")
    suspend fun deleteComment(
        @Header ("Authorization") BearerToken: String,
        @Path("board_id") boardId: Long,
        @Path("comment_id") commentId: Long
    ): Response<BasicResponse>

    // 게시글 해결완료
    @PATCH("boards/{board_id}/solved")
    suspend fun postSolve(
        @Header ("Authorization") BearerToken: String,
        @Path("board_id") boardId: Long,
        @Body solveRequest: SolveRequest
    ): Response<BasicResponse>

    // 인기 게시물 조회
    @GET("boards/best")
    suspend fun getPopularPost() : Response<PopularPostResponse>

}