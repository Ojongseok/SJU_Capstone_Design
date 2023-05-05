package sju.sejong.capstonedesign.view.main.inspect

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class InspectResultFragment: Fragment() {
    private var _binding: FragmentInspectResultBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<InspectResultFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentInspectResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBarChart()
        setImg()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnInspectResultCapture.setOnClickListener {
            val container = requireActivity().window.decorView
            val screenShot = ScreenShot(container)

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
    }

    fun ScreenShot(view: View): File? {
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

    private fun setImg() {
        val img = args.img
        binding.ivInspectResult.setImageURI(img)
    }

    private fun setBarChart() {
        binding.pieChart.setUsePercentValues(true)

        // data set
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(3f, "정상"))
        entries.add(PieEntry(84f, "딸기잿빛곰팡이병"))
        entries.add(PieEntry(6f, "딸기흰가루병"))
        entries.add(PieEntry(7f, "진단 불가"))

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
            textSize = 14f
            formSize = 14f
            formToTextSpace = 6f
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}