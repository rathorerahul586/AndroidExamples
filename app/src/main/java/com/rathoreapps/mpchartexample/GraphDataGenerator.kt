package com.rathoreapps.mpchartexample

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.first
import kotlin.collections.forEach

class GraphDataGenerator(val context: Context) {
    var dataSource: DataSource = DataSource()
    var rowData: FuelStatisticResponseData? = null

   // var keySetReversed: List<String> = java.util.ArrayList<String>(keyList)

    init {
        rowData = dataSource.loadJSONFromAsset(context)
    }

    fun getIgnitionData(): List<EventDataModel>? {
        return rowData?.data?.first()?.events?.ignitionOffAlert
    }

    fun getFuelData(): List<SensorValue>? {
        return rowData?.data?.first()?.sensors?.first()?.values
    }

    fun generateIgnitionGraph(): List<ILineDataSet> {
        val timeValuesData = getIgnitionData()

        val dataSets: MutableList<ILineDataSet> = ArrayList()

        timeValuesData?.let { eventList ->
            val barPoints: MutableList<Entry> = ArrayList()
            var barPoint1: Entry
            var barPoint2: Entry
            var dataSet: LineDataSet
            eventList.forEach { eventData ->
                barPoint1 = eventData.startTime?.toFloat()?.let { Entry(it, 50f) }!!
                barPoint2 = eventData.endTime?.toFloat()?.let { Entry(it, 50f) }!!

                barPoints.add(barPoint1)
                barPoints.add(barPoint2)
                dataSet = LineDataSet(barPoints, "Ignition Off")
                dataSet.mode = LineDataSet.Mode.STEPPED
                dataSet.color = ContextCompat.getColor(context, R.color.green)
                dataSet.fillDrawable = ContextCompat.getDrawable(context, R.color.red)
                dataSet.setDrawCircles(false)
                dataSet.setDrawFilled(true)
                dataSet.setDrawValues(false)
                dataSets.add(dataSet)
            }
            Log.d("TAG", "generateIgnitionGraph: ${dataSets.size}")

        }
        return dataSets
    }

    fun generateFuelGraph(): List<Entry> {
        val listData = ArrayList<Entry>()
        val fuelValuesData = getFuelData()

        fuelValuesData?.forEach { sensorsDataModel ->

            sensorsDataModel.timeStamp?.toFloat()
                ?.let { timeStamp ->
                    sensorsDataModel.value?.let { value ->
                        Entry(
                            timeStamp,
                            value
                        )
                    }
                }
                ?.let { listData.add(it) }
        }
//        listData.add(Entry(0f, 10f, "hello"))
//        listData.add(Entry(3f, 20f))
//        listData.add(Entry(5f, 15f))
//        listData.add(Entry(6f, 10f))
//        listData.add(Entry(9f, 0f))
//        listData.add(Entry(12f, 20f))
//        listData.add(Entry(13f, 15f))
//        listData.add(Entry(15f, 10f))
        return listData
    }


}


