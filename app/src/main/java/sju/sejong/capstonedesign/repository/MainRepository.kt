package sju.sejong.capstonedesign.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import sju.sejong.capstonedesign.model.InspectResponse
import sju.sejong.capstonedesign.retrofit.RetrofitInstance.service
import javax.inject.Inject

class MainRepository @Inject constructor() {

    // 병해 검사
    suspend fun startInspect(jsonBody: RequestBody, file: MultipartBody.Part?) : Response<InspectResponse> {
        return service.startDiagnosis(jsonBody, file)
    }
}