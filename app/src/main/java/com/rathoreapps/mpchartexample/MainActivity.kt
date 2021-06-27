package com.rathoreapps.mpchartexample

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.landscape.*
import kotlinx.android.synthetic.main.portrait.*


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivityTAG"
    lateinit var graphDataGenerator: GraphDataGenerator
    val firstTimeStamp: Long = 1611599400
    var isLandscape = false
    var mAdaptiveCustomMarker: AdaptiveCustomMarker? = null
    var combineData: CombinedData? = null


    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 270

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        graphDataGenerator = GraphDataGenerator(this)
        initGraph()

        rotateButton.setOnClickListener {
            rotateGraph()
        }


    }

    private fun initGraph() {
        //val lineData = LineData(generateLineData())
        //val lineData2 = LineDataSet(generateLineData2(), "B")

        Log.d(TAG, "initGraph: ${graphDataGenerator.generateIgnitionGraph()}")
        val lineData = LineData()
        combineData = CombinedData()
        val lineData2 = LineDataSet(graphDataGenerator.generateFuelGraph(), "B")
        lineData2.color = ContextCompat.getColor(this, R.color.darkSkyBlue)
        lineData2.setCircleColor(ContextCompat.getColor(this, R.color.darkSkyBlue))
        lineData2.valueTextColor = ContextCompat.getColor(this, R.color.darkSkyBlue)
        lineData2.valueTextSize = 12f
        lineData.addDataSet(lineData2)
        combineData?.setData(lineData)


        val legendList: ArrayList<LegendEntry> = ArrayList()

//        for (i in 0 until lineChart.data.dataSetCount) {
//            val entry = LegendEntry()
//            val set: ILineDataSet = lineChart.data.dataSets[i]
//            val label = set.label
//            if (label.equals("Ignition Off", ignoreCase = true)) {
//                entry.form = Legend.LegendForm.CIRCLE
//                entry.formColor = set.color
//                entry.label = label
//            } else if (label.equals("B", ignoreCase = true)) {
//                entry.form = Legend.LegendForm.LINE
//                entry.formLineWidth = 1f
//                entry.formSize = 10f
//                entry.formColor = set.color
//                entry.label = label
//            } else if (label == "C") {
//                entry.form = Legend.LegendForm.SQUARE
//                entry.formColor = set.color
//                entry.label = label
//            } else if (entry.formColor == ColorTemplate.COLOR_NONE ||
//                entry.formColor == 0
//            ) {
//                entry.form = Legend.LegendForm.EMPTY
//                entry.label = label
//            }
//            if (!legendList.contains(entry))
//                legendList.add(entry)
//        }

//        val l: Legend = lineChart.legend
//        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
//        l.setCustom(legendList)
        mAdaptiveCustomMarker = AdaptiveCustomMarker(this)

        drawPortraitChart()
        drawLandscapeChart()
    }

    private fun generateLineData(): List<ILineDataSet> {
        val timeValuesData = arrayListOf(1, 5, 6, 7, 9, 15)
        val timeValues: MutableList<Int> = ArrayList()
        timeValues.addAll(timeValuesData)

        val areaValuesData: MutableList<Int> = ArrayList()
        val areaValues = arrayListOf(20, 9, 12)
        areaValuesData.addAll(areaValues)

        val dataSets: MutableList<ILineDataSet> = ArrayList()
        var j = 0
        for (i in 0 until areaValues.size) {
            val barPoints: MutableList<Entry> = ArrayList()
            var barPoint1: Entry
            var barPoint2: Entry
            var dataSet: LineDataSet? = null
            if (areaValues[i] >= 0) {
                barPoint1 = Entry(timeValues[j].toFloat(), areaValues[i].toFloat())
                barPoint2 = Entry(timeValues[j + 1].toFloat(), areaValues[i].toFloat())
                barPoints.add(barPoint1)
                barPoints.add(barPoint2)
                Log.d("TAG", "generateLineData: $j")
                Log.d("TAG", "generateLineData:barPoint1 $barPoint1 barpoint2: $barPoint2")
                dataSet = LineDataSet(barPoints, "A")
                dataSet.mode = LineDataSet.Mode.LINEAR
                dataSet.color = ContextCompat.getColor(this, R.color.red)
                dataSet.fillDrawable = ContextCompat.getDrawable(this, R.color.red)

            }
            dataSet?.setDrawCircles(false)
            dataSet?.setDrawFilled(true)
            dataSet?.setDrawValues(false)
            dataSet?.let { dataSets.add(it) }
            j += 2
        }

        return dataSets
    }

    private fun generateLineData2(): List<Entry> {
        val listData = ArrayList<Entry>()
        listData.add(Entry(0f, 10f, "hello"))
        listData.add(Entry(3f, 20f))
        listData.add(Entry(5f, 15f))
        listData.add(Entry(6f, 10f))
        listData.add(Entry(9f, 0f))
        listData.add(Entry(12f, 20f))
        listData.add(Entry(13f, 15f))
        listData.add(Entry(15f, 10f))
        return listData
    }

    private fun initSeekBarZooming() {
//        val xRangeUnit = lineChart.xRange / 100
//        rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
//            override fun onStartTrackingTouch(slider: RangeSlider) {
//                Log.d(
//                    TAG,
//                    "onStartTrackingTouch from:  ${slider.values[0]} to: ${slider.values[1]}"
//                )
//
//            }
//
//            override fun onStopTrackingTouch(slider: RangeSlider) {
//                Log.d(
//                    TAG,
//                    "onStopTrackingTouch from:  ${slider.values[0]} to: ${slider.values[1]}"
//                )
//            }
//        })
//
//        rangeSlider.addOnChangeListener(RangeSlider.OnChangeListener { slider, _, _ ->
//            lineChart.xAxis.axisMinimum = firstTimeStamp + slider.values[0] * xRangeUnit
//            lineChart.xAxis.axisMaximum = firstTimeStamp + slider.values[1] * xRangeUnit
//            // lineChart.setVisibleXRange(slider.values[0] * xRangeUnit, slider.values[1] * xRangeUnit)
//
//            lineChart.notifyDataSetChanged()
//
//            lineChart.invalidate()
//            Log.d(
//                TAG,
//                "onValueChange from: ${slider.values[0] * xRangeUnit} to: ${slider.values[1] * xRangeUnit}"
//            )
//        })

    }

    private fun drawLandscapeChart() {

        fuelChart.xAxis.labelRotationAngle = -45f
        //lineChart.xAxis.axisMinimum = firstTimeStamp.toFloat()
        fuelChart.xAxis.valueFormatter = XAxisValueFormatter()
        fuelChart.setPinchZoom(false)
        //initSeekBarZooming()
        fuelChart.setClipValuesToContent(true)
        fuelChart.isScaleYEnabled = false
        fuelChart.xAxis.enableGridDashedLine(10f, 5f, 0f)
        fuelChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        fuelChart.axisRight.isEnabled = false
        fuelChart.axisLeft.axisLineWidth = 1f
        fuelChart.axisLeft.enableGridDashedLine(10f, 5f, 0f)
        fuelChart.drawOrder = arrayOf(
            DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER
        )
        mAdaptiveCustomMarker?.chartView = lineChart
        fuelChart.marker = mAdaptiveCustomMarker

        fuelChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(entry: Entry, highlight: Highlight) {

                Log.d(TAG, "onValueSelected:entry ${entry.data}")

            }

            override fun onNothingSelected() {}
        })

        fuelChart.data = combineData
        fuelChart.invalidate()

        change_view_tv.setOnClickListener { rotateGraph() }
    }

    private fun drawPortraitChart() {
        lineChart.xAxis.labelRotationAngle = -45f
        //lineChart.xAxis.axisMinimum = firstTimeStamp.toFloat()
        lineChart.xAxis.valueFormatter = XAxisValueFormatter()
        lineChart.setPinchZoom(false)
        //initSeekBarZooming()
        lineChart.setClipValuesToContent(true)
        lineChart.isScaleYEnabled = false
        lineChart.xAxis.enableGridDashedLine(10f, 5f, 0f)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.axisRight.isEnabled = false
        lineChart.axisLeft.axisLineWidth = 1f
        lineChart.axisLeft.enableGridDashedLine(10f, 5f, 0f)
        lineChart.drawOrder = arrayOf(
            DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER
        )
        mAdaptiveCustomMarker?.chartView = lineChart
        lineChart.marker = mAdaptiveCustomMarker

        lineChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(entry: Entry, highlight: Highlight) {

                Log.d(TAG, "onValueSelected:entry ${entry.data}")

            }

            override fun onNothingSelected() {}
        })

        lineChart.data = combineData
        lineChart.invalidate()
    }

    private fun rotateGraph() {
        if (isLandscape){
            isLandscape = true
            portrait_chart.visibility = View.VISIBLE
            landscape_chart.visibility = View.GONE
        }
        else {
            isLandscape = false
            landscape_chart.visibility = View.VISIBLE
            portrait_chart.visibility = View.GONE
        }

//        if (rotateLayout.getAngle() == HORIZONTAL) {
//            supportActionBar?.hide()
//            // Hide status bar
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                window.insetsController?.hide(WindowInsets.Type.statusBars())
//            } else {
//                window.setFlags(
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN
//                )
//            }
//            rotateLayout.setAngle(VERTICAL)
////            lineChart.rotation = VERTICAL
////            rotateButton.rotation = VERTICAL
////            rangeSlider.rotation = VERTICAL
//            lineChart.invalidate()
//        } else {
//            // Do Portrait calculation
//            supportActionBar?.show()
//            // Show status bar
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                window.insetsController?.show(WindowInsets.Type.statusBars())
//            } else {
//                window.setFlags(
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN
//                )
//            }
//
//
//            rotateLayout.setAngle(HORIZONTAL)
////            lineChart.rotation = HORIZONTAL
//
//        }
    }


}