package com.rathoreapps.mpchartexample

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.marker_view.view.*


class CustomMarker(context: Context) : MarkerView(context, R.layout.marker_view) {
    override fun refreshContent(entry: Entry?, highlight: Highlight?) {

        tvTitle.text = entry?.data.toString()
        super.refreshContent(entry, highlight)
    }

    override fun getOffsetForDrawingAtPoint(xpos: Float, ypos: Float): MPPointF {


        return MPPointF(-width / 2f, -height - 10f)
    }
}