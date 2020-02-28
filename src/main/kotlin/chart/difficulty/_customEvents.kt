@file:Suppress("ClassName")

package chart.difficulty

import com.google.gson.annotations.SerializedName

data class _customEvents (
    @SerializedName("_time") var _time: Float,
    @SerializedName("_type") var _type: String,
    @SerializedName("_data") var _data: _customEventCustomData?
)
data class _customEventCustomData(
    @SerializedName("name") var name: String?,
    @SerializedName("offset") var offset: List<Double>?,
    @SerializedName("angle") var angle: Double?,
    @SerializedName("axis") var axis: List<Double>?,
    @SerializedName("duration") var duration: Double?,
    @SerializedName("start") var start: _customEventCustomDataPosition?,
    @SerializedName("end") var end: _customEventCustomDataPosition?,
    @SerializedName("value") var value: Any?
)

data class _customEventCustomDataPosition(
    @SerializedName("offset") var offset: List<Double>?,
    @SerializedName("angle") var angle: Double?,
    @SerializedName("axis") var axis: List<Double>?
)

