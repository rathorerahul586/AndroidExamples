package com.rathoreapps.mpchartexample

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader

class DataSource {
    fun loadJSONFromAsset(context: Context): FuelStatisticResponseData? {
        var data: FuelStatisticResponseData? = null
        try {
            val ins = context.assets.open("data.json")
            val gson = Gson()
            val reader: Reader = InputStreamReader(ins)
            val gsonObj: FuelStatisticResponseData =
                gson.fromJson(reader, FuelStatisticResponseData::class.java)

            data = gsonObj
            Log.d(TAG, "loadJSONFromAsset: ${gsonObj.isSuccess}")
            Log.d(
                TAG,
                "loadJSONFromAsset size: ${gsonObj.data?.get(0)?.events?.ignitionOffAlert?.size}"
            )

        } catch (e: IOException) {
            Log.d(TAG, "loadJSONFromAsset:IOException ${e.message}")
            e.printStackTrace()
        }
        return data
    }

    companion object {
        const val TAG = "Repository"
    }
}