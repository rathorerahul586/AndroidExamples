package com.rathoreapps.mpchartexample

import com.google.gson.annotations.SerializedName

data class FuelStatisticResponseData(
    @SerializedName("success")
    val isSuccess: Boolean? = null,
    @SerializedName("data")
    val data: List<DataModel>? = null
)

data class DataModel(
    @SerializedName("timeline")
    val timelineList: List<Timeline>? = null,
    @SerializedName("events")
    val events: Events? = null,
    @SerializedName("sensors")
    val sensors: List<SensorsDataModel>? = null
)

data class Timeline(
    @SerializedName("type")
    val type: Int? = null,
    @SerializedName("st")
    val timelineStartTime: Long? = null,
    @SerializedName("et")
    val timelineEndTime: Long? = null
)

data class Events(
    @SerializedName(IGNITION_OFF_ALERT)
    val ignitionOffAlert: List<EventDataModel>? = null,
    @SerializedName(REFUELING_ALERT)
    val refuelingAlert: List<EventDataModel>? = null,
    @SerializedName(POSSIBLE_FUEL_THEFT_ALERT)
    val possibleFuelTheftAlert: List<EventDataModel>? = null
) {
    companion object {
        const val IGNITION_OFF_ALERT = "9"
        const val REFUELING_ALERT = "10"
        const val POSSIBLE_FUEL_THEFT_ALERT = "11"
    }
}

data class EventDataModel(
    @SerializedName("v")
    val value: Float? = null,
    @SerializedName("u")
    val unit: String? = null,
    @SerializedName("st")
    val startTime: Long? = null,
    @SerializedName("et")
    val endTime: Long? = null,
    @SerializedName("t")
    val timeStamp: Long? = null,
    @SerializedName("sl")
    val startLocation: EventLocationModel? = null,
    @SerializedName("el")
    val endLocation: EventLocationModel? = null,
    @SerializedName("l")
    val eventLocation: EventLocationModel? = null
)

data class EventLocationModel(
    @SerializedName("lat")
    var lat: Double? = null,

    @SerializedName("long")
    var lng: Double? = null,

    @SerializedName("address")
    var address: String? = null
)

data class SensorsDataModel(
    @SerializedName("uuid")
    val uuid: Int? = null,
    @SerializedName("display_name")
    val displayName: String? = null,
    @SerializedName("value_type")
    val valueType: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("values")
    val values: List<SensorValue>? = null
)

data class SensorValue(
    @SerializedName("t")
    val timeStamp: Long? = null,
    @SerializedName("v")
    val value: Float? = null
)
