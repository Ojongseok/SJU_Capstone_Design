package com.example.capstonedesign.view.main

import android.graphics.Color
import android.icu.text.Transliterator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstonedesign.databinding.FragmentInspectResultBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS

class InspectResultFragment: Fragment() {
    private var _binding: FragmentInspectResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentInspectResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBarChart()
    }

    private fun setBarChart() {
        binding.pieChart.setUsePercentValues(true)

        // data set
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(1f, "Apple"))
        entries.add(PieEntry(34f, "Mango"))
        entries.add(PieEntry(29f, "RedOrange"))
        entries.add(PieEntry(36f, "Other"))


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