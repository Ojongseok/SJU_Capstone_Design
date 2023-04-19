package com.example.capstonedesign.model.openapi

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "service")
data class PesticideDetailResponse(
    @PropertyElement(name="pestiKorName")
    val pestiKorName: String?,
    @PropertyElement(name="useName")
    val useName: String?,
    @PropertyElement(name="compName")
    val compName: String?,
    @PropertyElement(name="pestiBrandName")
    val pestiBrandName: String?,
    @PropertyElement(name="pestiEngName")
    val pestiEngName: String?,
    @PropertyElement(name="regCpntQnty")
    val regCpntQnty: String?,
    @PropertyElement(name="toxicGubun")
    val toxicGubun: String?,
    @PropertyElement(name="toxicName")
    val toxicName: String?,
    @PropertyElement(name="fishToxicGubun")
    val fishToxicGubun: String?,
    @PropertyElement(name="cropName")
    val cropName: String?,
    @PropertyElement(name="diseaseWeedName")
    val diseaseWeedName: String?,
    @PropertyElement(name="pestiUse")
    val pestiUse: String?,
    @PropertyElement(name="dilutUnit")
    val dilutUnit: String?,
    @PropertyElement(name="useSuittime")
    val useSuittime: String?,
    @PropertyElement(name="useNum")
    val useNum: String?
)
