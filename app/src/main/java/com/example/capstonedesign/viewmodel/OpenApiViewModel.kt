package com.example.capstonedesign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstonedesign.model.CropDetailResponse
import com.example.capstonedesign.model.DiseaseDetailResponse
import com.example.capstonedesign.retrofit.OpenApiRetrofitInstance.API_KEY
import com.example.capstonedesign.retrofit.OpenApiRetrofitInstance.OpenApiRetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class OpenApiViewModel: ViewModel() {
    val cropList = MutableLiveData<List<Element>>()    // 작물 목록
    val diseaseGeneratedMonthly1 = MutableLiveData<List<Element>>()    // 경보
    val diseaseGeneratedMonthly2 = MutableLiveData<List<Element>>()    // 주의보
    val diseaseGeneratedMonthly3 = MutableLiveData<List<Element>>()    // 예보
    val cropDetailInfo = MutableLiveData<CropDetailResponse>()    // 작물별 상세정보
    val pbHome = MutableLiveData<Boolean>()    // ProgressBar 홈
    val pbCropList = MutableLiveData<Boolean>()    // ProgressBar 검색
    val pbCropDetailInfo = MutableLiveData<Boolean>()    // ProgressBar 상세정보
    val diseaseDetailInfo = MutableLiveData<DiseaseDetailResponse>()    // 병 상세정보
    val diseaseDetailInfoCompleted = MutableLiveData<Boolean>()

    fun setDiseaseGeneratedMonthly() = CoroutineScope(Dispatchers.IO).launch {
        val url = "https://ncpms.rda.go.kr/npms/NewIndcUserR.np?indcMon=&indcSeq=206&ncpms.cmm.token.html.TOKEN=d9158d3782321ff65ee9da4ca2ac9ef6&pageIndex=1&sRegistDatetm=&eRegistDatetm=&sCrtpsnNm=&sIndcSj="
        val doc = Jsoup.connect(url).get()

//        val data1 = doc.select("li.watch").select("ul.afterClear").select("li").toMutableList()
        val data2 = doc.select("li.watch").select("ul.afterClear").select("li").toMutableList()
        val data3 = doc.select("li.forecast").select("ul.afterClear").select("li").toMutableList()

        diseaseGeneratedMonthly2.postValue(data2)
        diseaseGeneratedMonthly3.postValue(data3)
        pbHome.postValue(true)
    }

    fun searchDetailCropInfo(cropName: String) = viewModelScope.launch {
        val response = OpenApiRetrofitService.searchDetailCropInfo(API_KEY, "SVC01", "AA001", cropName)

        cropDetailInfo.postValue(response)
        pbCropDetailInfo.postValue(true)
    }

    fun searchDiseaseDetailInfo(sickKey: String) = viewModelScope.launch {
        val response = OpenApiRetrofitService.searchDiseaseDetailInfo(API_KEY, "SVC05", sickKey)

        diseaseDetailInfo.postValue(response)
        diseaseDetailInfoCompleted.postValue(true)
    }

    fun setCropList() = CoroutineScope(Dispatchers.IO).launch {
        val url = "https://ncpms.rda.go.kr/npms/VegitablesImageListR.np"
        val doc = Jsoup.connect(url).get()
        val data = doc.select("ul.floatDiv.mt20.ce.photoSearch").select("li").toMutableList()

        cropList.postValue(data)    // .value는 메인 쓰레드에서, postValue는 백그라운드 쓰레드에서
        pbCropList.postValue(true)
    }
}