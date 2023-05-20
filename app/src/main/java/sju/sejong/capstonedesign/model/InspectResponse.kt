package sju.sejong.capstonedesign.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class InspectResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result : InspectResult
)
@Parcelize
data class InspectResult(
    val isCorrect: Boolean,
    val errnum : Int,
    val diseaseName: String,
    val inCropInfo: String,
    val outCropInfo: String,
    val classProbabilityList: List<Float>,
    val errorMessage: String
) : Parcelable
