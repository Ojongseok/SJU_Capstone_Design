package sju.sejong.capstonedesign.model.login

data class RegionDiseaseResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<RegionDiseaseResult>
)

data class RegionDiseaseResult(
    val diseaseNames: String,
    val diseaseNum: Long
)
