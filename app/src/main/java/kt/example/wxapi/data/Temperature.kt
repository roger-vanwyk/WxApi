package kt.example.wxapi.data

import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("day") var degreeDay: Double,
    @SerializedName("night") var degreeNight: Double,
    @SerializedName("temp_min") var degreeMin: Double,
    @SerializedName("temp_max") var degreeMax: Double
)