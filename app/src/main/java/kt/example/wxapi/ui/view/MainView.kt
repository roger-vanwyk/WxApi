package kt.example.wxapi.ui.view

import kt.example.wxapi.ErrorTypes
import kt.example.wxapi.ui.ForecastItemViewModel

interface MainView {
    val presenter: Any

    fun showSpinner()
    fun hideSpinner()
    fun updateForecast(forecasts: List<ForecastItemViewModel>)
    fun showErrorToast(errorType: ErrorTypes)
}