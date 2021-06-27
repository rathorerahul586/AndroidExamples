package com.rathoreapps.mpchartexample

import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class XAxisValueFormatter(): IAxisValueFormatter {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        val format2 = SimpleDateFormat("dd MMM YYYY HH:mm:ss")
        Log.d("TAG", "getFormattedValue: ${value.toLong() * 1000 }")
        return format2.format(Date(value.toLong() * 1000 ))

    }
}