package com.rathoreapps.mpchartexample

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import kotlinx.android.synthetic.main.marker_view.view.*
import kotlin.math.roundToInt


class AdaptiveCustomMarker2(context: Context?) : MarkerView(context, R.layout.marker_view) {
    //private val stepListModels: List<StepListModel>
    private var index = 0
    private var oldIndex = -1
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        Log.e("Text", " : " + e?.y?.roundToInt())
        tvTitle.text = e?.data.toString()
    }


    override fun draw(canvas: Canvas, posx: Float, posy: Float) {
        // Check marker position and update offsets.
        val uiScreenWidth = getResources().getDisplayMetrics().widthPixels;
        var posx = posx
        val w = width
        if (uiScreenWidth - posx - w < w) {
            posx -= w.toFloat()
        }

        // translate to the correct position and draw
        canvas.translate(posx, posy)
        draw(canvas)
        canvas.translate(-posx, -posy)
    }
}