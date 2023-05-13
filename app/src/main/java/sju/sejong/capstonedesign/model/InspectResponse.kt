package sju.sejong.capstonedesign.model

data class InspectResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result : InspectResult
)
data class InspectResult(
    val isCorrect: Boolean,
    val diseaseName: String,
    val inCropInfo: String,
    val outCropInfo: String,
    val classProbabilityList: List<Float>,
    val errorMessage: String
)
