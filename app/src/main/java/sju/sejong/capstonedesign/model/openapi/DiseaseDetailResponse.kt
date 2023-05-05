package sju.sejong.capstonedesign.model.openapi

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "service")
data class DiseaseDetailResponse(
    @PropertyElement(name="preventionMethod")
    val preventionMethod: String?,
    @PropertyElement(name="symptoms")
    val symptoms: String?,
    @Element(name = "imageList")
    val imageList: ImageListItems?,
    @Element(name = "virusList")
    val virusList: VirusList?,
    @PropertyElement(name="developmentCondition")
    val developmentCondition: String?,
    @PropertyElement(name="infectionRoute")
    val infectionRoute: String?,
    @PropertyElement(name="cropName")
    val cropName: String?,
    @PropertyElement(name="sickNameChn")
    val sickNameChn: String?,
    @PropertyElement(name="sickNameKor")
    val sickNameKor: String?,
    @PropertyElement(name="sickNameEng")
    val sickNameEng: String?,
    @PropertyElement(name="chemicalPrvnbeMth")
    val chemicalPrvnbeMth: String?,
)

@Xml(name= "item")
data class ImageListItems(
    @Element(name="item")
    val item: List<ImageListItem>?
)

@Xml(name= "item")
data class VirusList(
    @Element(name="item")
    val item: List<VirusListItem>?
)

@Xml
data class ImageListItem(
    @PropertyElement(name="imageTitle")
    val imageTitle: String?,
    @PropertyElement(name="iemSpchcknNm")
    val iemSpchcknNm: String?,
    @PropertyElement(name="image")
    val image: String?,
    @PropertyElement(name="iemSpchcknCode")
    val iemSpchcknCode: String?,
)

@Xml
data class VirusListItem(
    @PropertyElement(name="sfeNm")
    val sfeNm: String?,
    @PropertyElement(name="virusName")
    val virusName: String?,
)