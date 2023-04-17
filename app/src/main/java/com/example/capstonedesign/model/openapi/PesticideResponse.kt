package com.example.capstonedesign.model.openapi

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "service")
data class PesticideResponse(
    @PropertyElement(name="totalCount")
    val totalCount: Int?,
    @Element(name = "list")
    val list: PesticideItems?,
)

@Xml(name= "item")
data class PesticideItems(
    @Element(name="item")
    val item: List<PesticideItem>?
)

@Xml
data class PesticideItem(
    @PropertyElement(name="pestiCode")
    val pestiCode: String?,
    @PropertyElement(name="diseaseUseSeq")
    val diseaseUseSeq: String?,
    @PropertyElement(name="cropName")
    val cropName: String?,
    @PropertyElement(name="diseaseWeedName")
    val diseaseWeedName: String?,
    @PropertyElement(name="useName")
    val useName: String?,
    @PropertyElement(name="pestiKorName")
    val pestiKorName: String?,
    @PropertyElement(name="pestiBrandName")
    val pestiBrandName: String?,
    @PropertyElement(name="compName")
    val compName: String?,
    @PropertyElement(name="engName")
    val engName: String?,
    @PropertyElement(name="cmpaItmNm")
    val cmpaItmNm: String?,
    @PropertyElement(name="indictSymbl")
    val indictSymbl: String?,
    @PropertyElement(name="applyFirstRegDate")
    val applyFirstRegDate: String?,
    @PropertyElement(name="cropCd")
    val cropCd: String?,
    @PropertyElement(name="cropLrclCd")
    val cropLrclCd: String?,
    @PropertyElement(name="cropLrclNm")
    val cropLrclNm: String?,
    @PropertyElement(name="pestiUse")
    val pestiUse: String?,
    @PropertyElement(name="dilutUnit")
    val dilutUnit: String?,
    @PropertyElement(name="useSuittime")
    val useSuittime: String?,
    @PropertyElement(name="useNum")
    val useNum: String?,
    @PropertyElement(name="wafindex")
    val wafindex: String?,
)
