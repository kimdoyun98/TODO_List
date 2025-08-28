package com.project.home.bindingadapter

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.project.core.ui.R
import com.project.home.model.PeriodStatistics

object PieChartBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:set")
    fun pieChartSetting(chart: PieChart, periodStatistics: PeriodStatistics) {
        chart.apply {
            data = if (periodStatistics.total == 0) emptyPieDate(chart.context)
            else pieData(
                periodStatistics,
                chart.context
            )
            description.isEnabled = false // 차트 설명 비활성화
            legend.isEnabled = false // 하단 설명 비활성화
            isRotationEnabled = true // 차트 회전 활성화
            setEntryLabelColor(Color.WHITE) // label 색상
            animateY(1400, Easing.EaseInOutQuad) // 1.4초 동안 애니메이션 설정
        }

        chart.animate()
    }

    private fun pieData(periodStatistics: PeriodStatistics, context: Context): PieData {
        val total = periodStatistics.total.toDouble()
        val success = periodStatistics.success.toDouble()
        val fail = total - success

        val list = listOf(
            PieEntry((success / total * 100).toFloat(), "Success"),
            PieEntry(((fail / total) * 100).toFloat(), "Fail")
        )

        val dataSet = PieDataSet(list, "PeriodRoutineLog").apply {
            valueTextSize = 16F
            setDrawValues(false)
            colors = listOf(
                ContextCompat.getColor(context, R.color.green),
                ContextCompat.getColor(context, R.color.red)
            )
        }

        return PieData(dataSet)
    }

    private fun emptyPieDate(context: Context): PieData {
        val list = listOf(PieEntry(100F, "None"))
        val dataSet = PieDataSet(list, "PeriodRoutineLog").apply {
            valueTextSize = 16F
            setDrawValues(false)
            colors = listOf(ContextCompat.getColor(context, R.color.blue))
        }

        return PieData(dataSet)
    }
}
