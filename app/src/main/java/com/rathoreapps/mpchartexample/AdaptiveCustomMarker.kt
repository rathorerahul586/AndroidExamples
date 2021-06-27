package com.rathoreapps.mpchartexample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.marker_view.view.*


class AdaptiveCustomMarker(context: Context?) : MarkerView(context, R.layout.marker_view) {
    //private val stepListModels: List<StepListModel>
    private var index = 0
    private var oldIndex = -1
    override fun refreshContent(e: Entry?, highlight: Highlight) {
        super.refreshContent(e, highlight)
        index = highlight.dataSetIndex //This method is used to obtain which polyline is

        tvTitle.text = e?.data.toString()

        tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black))
    }

    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        val offset = offset
        val chart = chartView
        Log.d("TAG", "getOffsetForDrawingAtPoint: $chart")
        val width = width.toFloat()
        val height = height.toFloat()
        // posY \posX refers to the position of the upper left corner of the markerView on the chart
//Handle Y direction
        if (posY <= height + ARROW_SIZE) { // If the y coordinate of the point is less than the height of the markerView, if it is not processed, it will exceed the upper boundary. After processing, the arrow is up at this time, and we need to move the icon down by the size of the arrow
            offset.y = ARROW_SIZE.toFloat()
        } else { //Otherwise, it is normal, because our default is that the arrow is facing downwards, and then the normal offset is that you need to offset the height of the markerView and the arrow size, plus a stroke width, because you need to see the upper border of the dialog box
            offset.y = -height - ARROW_SIZE - STOKE_WIDTH // 40 arrow height   5 stroke width
        }
        //Processing the X direction, divided into 3 cases, 1. On the left side of the chart 2. On the middle of the chart 3. On the right side of the chart
//
        if (posX > chart.width - width) { //If it exceeds the right boundary, offset the width of the markerView to the left
            offset.x = -width
        } else { //By default, no offset (because the point is in the upper left corner)
            offset.x = 0f
            if (posX > width / 2) { //If it is greater than half of the markerView, the arrow is in the middle, so it is offset by half the width to the right
                offset.x = -(width / 2)
            }
        }
        return offset
    }

    override fun draw(canvas: Canvas, posX: Float, posY: Float) {
        val paint = Paint() //The brush for drawing the border
        paint.strokeWidth = STOKE_WIDTH
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.color = ContextCompat.getColor(context, R.color.darkSkyBlue)

        val whitePaint = Paint() //Draw a brush with a white background
        whitePaint.style = Paint.Style.FILL
        whitePaint.color = Color.WHITE
        val chart = chartView
        val width = width.toFloat()
        val height = height.toFloat()
        val offset = getOffsetForDrawingAtPoint(posX, posY)

        val saveId: Int = canvas.save()
        var path: Path
        if (posY < height + ARROW_SIZE) { //Processing exceeds the upper boundary
            path = Path()
            path.moveTo(0f, 0f)
            if (posX > chart.width - width) { //Exceed the right boundary
                path.lineTo(width - ARROW_SIZE, 0f)
                path.lineTo(width, -ARROW_SIZE + CIRCLE_OFFSET)
                path.lineTo(width, 0f)
            } else {
                if (posX > width / 2) { //In the middle of the chart
                    path.lineTo(width / 2 - ARROW_SIZE / 2, 0f)
                    path.lineTo(width / 2, -ARROW_SIZE + CIRCLE_OFFSET)
                    path.lineTo(width / 2 + ARROW_SIZE / 2, 0f)
                } else { //Exceed the left margin
                    path.lineTo(0f, -ARROW_SIZE + CIRCLE_OFFSET)
                    path.lineTo(0f + ARROW_SIZE, 0f)
                }
            }
            path.lineTo(0 + width, 0f)
            path.lineTo(0 + width, 0 + height)
            path.lineTo(0f, 0 + height)
            path.lineTo(0f, 0f)
            path.offset(posX + offset.x, posY + offset.y)
        } else { //Does not exceed the upper boundary
            path = Path()
            path.moveTo(0f, 0f)
            path.lineTo(0 + width, 0f)
            path.lineTo(0 + width, 0 + height)
            if (posX > chart.width - width) {
                path.lineTo(width, height + ARROW_SIZE - CIRCLE_OFFSET)
                path.lineTo(width - ARROW_SIZE, 0 + height)
                path.lineTo(0f, 0 + height)
            } else {
                if (posX > width / 2) {
                    path.lineTo(width / 2 + ARROW_SIZE / 2, 0 + height)
                    path.lineTo(width / 2, height + ARROW_SIZE - CIRCLE_OFFSET)
                    path.lineTo(width / 2 - ARROW_SIZE / 2, 0 + height)
                    path.lineTo(0f, 0 + height)
                } else {
                    path.lineTo(0f + ARROW_SIZE, 0 + height)
                    path.lineTo(0f, height + ARROW_SIZE - CIRCLE_OFFSET)
                    path.lineTo(0f, 0 + height)
                }
            }
            path.lineTo(0f, 0f)
            path.offset(posX + offset.x, posY + offset.y)
        }

        // translate to the correct position and draw
        canvas.drawPath(path, whitePaint)
        canvas.drawPath(path, paint)
        canvas.translate(posX + offset.x, posY + offset.y)
        draw(canvas)
        canvas.restoreToCount(saveId)
    }

    companion object {
        const val ARROW_SIZE = 15 // The size of the arrow
        private const val CIRCLE_OFFSET =
            0f //Because my turning point here is a circle, it needs to be offset to prevent it from pointing directly to the center of the circle
        private const val STOKE_WIDTH =
            1f //There is also a certain offset for the width of stroke_width
    }

    init {
    }
}