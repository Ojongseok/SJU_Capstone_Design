package sju.sejong.capstonedesign.view.main.inspect

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sju.sejong.capstonedesign.databinding.FragmentInspectResultBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import sju.sejong.capstonedesign.model.InspectResult
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class InspectResultFragment: Fragment() {
    private var _binding: FragmentInspectResultBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<InspectResultFragmentArgs>()
    private lateinit var result : InspectResult

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentInspectResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitData()
        setPieChart()

        binding.btnInspectResultCapture.setOnClickListener {
            val container = requireActivity().window.decorView
            val screenShot = screenShot(container)

            if (screenShot != null) {
                requireContext().sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(screenShot)))
                Toast.makeText(requireContext(), "진단결과가 갤러리에 저장되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnInspectResultShare.setOnClickListener {
            Toast.makeText(requireContext(), "준비중입니다.", Toast.LENGTH_SHORT).show()
        }

        binding.btnInspectResultCheck.setOnClickListener {
            val action = InspectResultFragmentDirections.actionFragmentInspectResultToFragmentHome()
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun setInitData() {
        result = args.result
        binding.ivInspectResult.setImageURI(args.img)

        Log.d("태그", result.toString())
    }

    private fun setPieChart() {
        binding.pieChart.setUsePercentValues(true)

        // data set
        val entries = ArrayList<PieEntry>()

        when (result.errnum) {
            1 -> {
                when (result.outCropInfo) {
                    "pepper " -> {
                        entries.add(PieEntry(result.classProbabilityList[0], "정상"))
                        entries.add(PieEntry(result.classProbabilityList[1], "고추마일드모틀바이러스병"))
                        entries.add(PieEntry(result.classProbabilityList[2], "고추점무늬병"))

                        val triple = arrayOf(result.classProbabilityList[0],result.classProbabilityList[1],result.classProbabilityList[2])
                        when (triple.indices.maxByOrNull { triple[it] } ?: -1) {
                            0 -> {
                                binding.tvInspectResultSummary.text = "정상입니다."
                            }
                            1 -> {
                                binding.tvInspectResultSummary.text = "고추마일드모틀바이러스병에 감염되었을 가능성이 높습니다."
                            }
                            2 -> {
                                binding.tvInspectResultSummary.text = "고추점무늬병에 감염되었을 가능성이 높습니다."
                            }
                        }
                    }
                    "strawberry" -> {
                        entries.add(PieEntry(result.classProbabilityList[3], "정상"))
                        entries.add(PieEntry(result.classProbabilityList[4], "딸기잿빛곰팡이병"))
                        entries.add(PieEntry(result.classProbabilityList[5], "딸기흰가루병"))

                        val triple = arrayOf(result.classProbabilityList[3],result.classProbabilityList[4],result.classProbabilityList[5])
                        when (triple.indices.maxByOrNull { triple[it] } ?: -1) {
                            0 -> {
                                binding.tvInspectResultSummary.text = "정상입니다."
                            }
                            1 -> {
                                binding.tvInspectResultSummary.text = "딸기잿빛곰팡이병에 감염되었을 가능성이 높습니다."
                            }
                            2 -> {
                                binding.tvInspectResultSummary.text = "딸기흰가루에 감염되었을 가능성이 높습니다."
                            }
                        }
                    }
                    "lettuce" -> {
                        entries.add(PieEntry(result.classProbabilityList[6], "정상"))
                        entries.add(PieEntry(result.classProbabilityList[7], "상추균핵병"))
                        entries.add(PieEntry(result.classProbabilityList[8], "상추노균병"))

                        val triple = arrayOf(result.classProbabilityList[6],result.classProbabilityList[7],result.classProbabilityList[8])
                        when (triple.indices.maxByOrNull { triple[it] } ?: -1) {
                            0 -> {
                                binding.tvInspectResultSummary.text = "정상입니다."
                            }
                            1 -> {
                                binding.tvInspectResultSummary.text = "상추균핵병에 감염되었을 가능성이 높습니다."
                            }
                            2 -> {
                                binding.tvInspectResultSummary.text = "상추노균병에 감염되었을 가능성이 높습니다."
                            }
                        }
                    }
                    "tomato" -> {
                        entries.add(PieEntry(result.classProbabilityList[9], "정상"))
                        entries.add(PieEntry(result.classProbabilityList[10], "토마토잎공팡이병"))
                        entries.add(PieEntry(result.classProbabilityList[11], "토마토황화잎말이바이러스병"))

                        val triple = arrayOf(result.classProbabilityList[9],result.classProbabilityList[10],result.classProbabilityList[11])
                        when (triple.indices.maxByOrNull { triple[it] } ?: -1) {
                            0 -> {
                                binding.tvInspectResultSummary.text = "정상입니다."
                            }
                            1 -> {
                                binding.tvInspectResultSummary.text = "토마토잎공팡이병에 감염되었을 가능성이 높습니다."
                            }
                            2 -> {
                                binding.tvInspectResultSummary.text = "토마토황화잎말이바이러스병에 감염되었을 가능성이 높습니다."
                            }
                        }
                    }

                }
            }
            2 -> {
                when (result.outCropInfo) {
                    "pepper " -> {
                        entries.add(PieEntry(result.classProbabilityList[0], "정상"))
                        entries.add(PieEntry(result.classProbabilityList[1], "고추마일드모틀바이러스병"))
                        entries.add(PieEntry(result.classProbabilityList[2], "고추점무늬병"))

                        val triple = arrayOf(result.classProbabilityList[0],result.classProbabilityList[1],result.classProbabilityList[2])
                        when (triple.indices.maxByOrNull { triple[it] } ?: -1) {
                            0 -> {
                                binding.tvInspectResultSummary.text = "정상인 것으로 확인되지만 감염이 의심됩니다. 다른 각도에서 촬영 후 다시 검사해주세요."
                            }
                            1 -> {
                                binding.tvInspectResultSummary.text = "고추마일드모틀바이러스병에 감염되었을 가능성이 있습니다."
                            }
                            2 -> {
                                binding.tvInspectResultSummary.text = "고추점무늬병에 감염되었을 가능성이 있습니다."
                            }
                        }
                    }
                    "strawberry" -> {
                        entries.add(PieEntry(result.classProbabilityList[3], "정상"))
                        entries.add(PieEntry(result.classProbabilityList[4], "딸기잿빛곰팡이병"))
                        entries.add(PieEntry(result.classProbabilityList[5], "딸기흰가루병"))

                        val triple = arrayOf(result.classProbabilityList[3],result.classProbabilityList[4],result.classProbabilityList[5])
                        when (triple.indices.maxByOrNull { triple[it] } ?: -1) {
                            0 -> {
                                binding.tvInspectResultSummary.text = "정상인 것으로 확인되지만 감염이 의심됩니다. 다른 각도에서 촬영 후 다시 검사해주세요."
                            }
                            1 -> {
                                binding.tvInspectResultSummary.text = "딸기잿빛곰팡이병에 감염되었을 가능성이 있습니다."
                            }
                            2 -> {
                                binding.tvInspectResultSummary.text = "딸기흰가루에 감염되었을 가능성이 있습니다."
                            }
                        }
                    }
                    "lettuce" -> {
                        entries.add(PieEntry(result.classProbabilityList[6], "정상"))
                        entries.add(PieEntry(result.classProbabilityList[7], "상추균핵병"))
                        entries.add(PieEntry(result.classProbabilityList[8], "상추노균병"))

                        val triple = arrayOf(result.classProbabilityList[6],result.classProbabilityList[7],result.classProbabilityList[8])
                        when (triple.indices.maxByOrNull { triple[it] } ?: -1) {
                            0 -> {
                                binding.tvInspectResultSummary.text = "정상인 것으로 확인되지만 감염이 의심됩니다. 다른 각도에서 촬영 후 다시 검사해주세요."
                            }
                            1 -> {
                                binding.tvInspectResultSummary.text = "상추균핵병에 감염되었을 가능성이 있습니다."
                            }
                            2 -> {
                                binding.tvInspectResultSummary.text = "상추노균병에 감염되었을 가능성이 있습니다."
                            }
                        }
                    }
                    "tomato" -> {
                        entries.add(PieEntry(result.classProbabilityList[9], "정상"))
                        entries.add(PieEntry(result.classProbabilityList[10], "토마토잎공팡이병"))
                        entries.add(PieEntry(result.classProbabilityList[11], "토마토황화잎말이바이러스병"))

                        val triple = arrayOf(result.classProbabilityList[9],result.classProbabilityList[10],result.classProbabilityList[11])
                        when (triple.indices.maxByOrNull { triple[it] } ?: -1) {
                            0 -> {
                                binding.tvInspectResultSummary.text = "정상인 것으로 확인되지만 감염이 의심됩니다. 다른 각도에서 촬영 후 다시 검사해주세요."
                            }
                            1 -> {
                                binding.tvInspectResultSummary.text = "토마토잎공팡이병에 감염되었을 가능성이 있습니다."
                            }
                            2 -> {
                                binding.tvInspectResultSummary.text = "토마토황화잎말이바이러스병에 감염되었을 가능성이 있습니다."
                            }
                        }
                    }

                }
            }
            3 -> {
                entries.add(PieEntry(100f, "진단 불가"))
                binding.tvInspectResultSummary.text = "작물 선택이 잘못되었을 가능성이 높습니다. 작물 종류와 첨부된 이미지를 확인 후 다시 검사해 주세요."
            }
            4 -> {
                entries.add(PieEntry(100f, "진단 불가"))
                binding.tvInspectResultSummary.text = "지원하지 않는 작물입니다."
            }
        }


        // add a lot of colors
        val colorsItems = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
        for (c in COLORFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
        colorsItems.add(ColorTemplate.getHoloBlue())

        val pieDataSet = PieDataSet(entries, "")
        pieDataSet.apply {
            colors = colorsItems
            valueTextColor = Color.BLACK
            valueTextSize = 18f
            valueFormatter = PercentFormatter()
            valueFormatter = PercentFormatter(binding.pieChart)
        }

        val pieData = PieData(pieDataSet)
        binding.pieChart.apply {
            data = pieData
            description.isEnabled = false
            isRotationEnabled = false
            centerText = "진단 결과"
//            setDrawEntryLabels(false)    // 차트 안에 설명 지워짐
            setUsePercentValues(true)
            setEntryLabelColor(Color.BLACK)
            setCenterTextSize(20f)
            animateY(1400, Easing.EaseInOutQuad)
            animate()

            extraTopOffset = 5f
            extraBottomOffset = 5f
            extraLeftOffset = -30f
            extraRightOffset = 25f
        }
        binding.pieChart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            orientation = Legend.LegendOrientation.VERTICAL
            form = Legend.LegendForm.CIRCLE
            textSize = 13f
            formSize = 13f
            formToTextSpace = 6f
        }
    }

    private fun screenShot(view: View): File? {
        view.isDrawingCacheEnabled = true //화면에 뿌릴때 캐시를 사용하게 한다
        val screenBitmap = view.drawingCache //캐시를 비트맵으로 변환
        val filename = System.currentTimeMillis().toString() + "screenshot.png"
        val file = File(
            Environment.getExternalStorageDirectory().toString() + "/Pictures",
            filename
        ) //Pictures폴더 screenshot.png 파일
        var os: FileOutputStream? = null
        try {
            os = FileOutputStream(file)
            screenBitmap.compress(Bitmap.CompressFormat.PNG, 90, os) //비트맵을 PNG파일로 변환
            os.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        view.isDrawingCacheEnabled = false
        return file
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}