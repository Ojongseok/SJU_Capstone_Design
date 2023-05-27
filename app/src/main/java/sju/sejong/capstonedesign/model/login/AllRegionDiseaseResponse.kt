package sju.sejong.capstonedesign.model.login

data class AllRegionDiseaseResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<RegionDiseaseResult>
)

data class RegionDiseaseResult(
    val region: String,
    val diseaseCount: Long
)