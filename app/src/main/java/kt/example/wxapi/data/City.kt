package kt.example.wxapi.data

data class City (
    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    var cityName: String?,
    @Json(name = "country")
    var country: String?,
)