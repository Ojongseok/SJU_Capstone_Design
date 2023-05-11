package sju.sejong.capstonedesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import sju.sejong.capstonedesign.model.InspectResponse
import sju.sejong.capstonedesign.repository.BoardRepository
import sju.sejong.capstonedesign.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {
    val inspectResponse = MutableLiveData<InspectResponse>()

    // 병해 검사
    fun startInspect(jsonBody: RequestBody, file: MultipartBody.Part?) = viewModelScope.launch {
        val response = repository.startInspect(jsonBody, file)
        inspectResponse.postValue(response.body())
    }
}