package kt.example.wxapi.ui.presenter

import kt.example.wxapi.api.OpenWeatherAPI
import kt.example.wxapi.ui.view.MainView
import kt.example.wxapi.data.WeatherResponse
import javax.inject.Inject

class MainPresenter(val view : MainView) {
    @Inject
    lateinit var api : OpenWeatherAPI

    val presenter = MainPresenter(this)

    private fun injectID() {
        DaggerOpenWeatherAPIComponent
            .builder()
            .openWeatherAPIModule()
            .build()
            .inject(presenter)
    }

    private fun initializeForecastList() {
        forecastList.apply {
            layoutManager = LinerLayoutManager(context)
            adapter = ForecastAdapter()
        }
    }

    fun getForecastForSevenDays(cityName : String) {
        if (BuildConfig.OPEN_WEATHER_API_KEY == "<4cabdde10f906e0d7c592cdde21a1d98>") {
            view.showErrorToast(ErrorTypes.MISSING_API_KEY)
            return
        }
        view.showSpinner()
        api.dailyForecast(cityName, 7).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(call: Call<WeatherResponse>, response : Response<WeatherResponse>) {
                response.body()?.let {
                    view.hideSpinner()
                }
                    ?: view.showErrorToast(ErrorTypes.NO_RESULT_FOUND)
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable) {
                view.showErrorToast(ErrorTypes.CALL_ERROR)
                t.printStackTrace()
            }
        })
    }

    private fun createListForView(weatherResponse: WeatherResponse) {
        val forecasts = mutableListOf<ForecastItemViewModel>()
        for(forecastDetail : ForecastDetail in weatherResponse.forecast) {
            val dayTemp = forecastDetail.temperature.dayTemperature
            val forecastItem = ForecastItemViewModel(degreeDay = dayTemp.toString(),
                date = forecastDetail.date,
                icon = forecastDetail.description[0].icon,
                description = forecastDetail.description[0].description)
            forecasts.add(forecastItem)
        }
        view.updateForecast(forecasts)
    }
}

private fun Any.enqueue(any: Any) {

}