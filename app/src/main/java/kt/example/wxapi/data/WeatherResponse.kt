package kt.example.wxapi.data

data class WeatherResponse (
    @Json(name = "base")
    val base: String?,
    @Json(name = "clouds")
    val clouds: Clouds?,
    @Json(name = "cod")
    val cod: Int?,
    @Json(name = "coord")
    val coord: Coord?,
    @Json(name = "dt")
    val dt: Int?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "main")
    val main: Main?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "weather")
    val weather: List<Weather>
)

annotation class Json(val name: String)

data class Clouds(
    @Json(name = "all")
    val all:Int?
)

data class Coord(
    @Json(name = "lat")
    val lat: Double?,
    @Json(name = "lon")
    val lon: Double?
)

@Parcelize
data class Main (
    @Json(name = "temp")
    val temp: Double?,
    @Json(name = "temp_min")
    val temp_min: Double?,
    @Json(name = "temp_max")
    val temp_max: Double?
)

annotation class Parcelize

@Parcelize
data class Weather (
    @Json(name = "description")
    val description: String?,
    @Json(name = "icon")
    val icon: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "main")
    val main: String?
)