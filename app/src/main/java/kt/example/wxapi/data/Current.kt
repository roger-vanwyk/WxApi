package kt.example.wxapi.data

import com.google.gson.annotations.SerializedName

data class Current (@SerializedName("dt")var date: Long,
                           @SerializedName("temp")var degreeDay: Temperature,
                           @SerializedName("weather")var description: List<WeatherDescription>,
                           @SerializedName("pressure")var pressure: Double,
                           @SerializedName("humidity")var humidity: Double)
