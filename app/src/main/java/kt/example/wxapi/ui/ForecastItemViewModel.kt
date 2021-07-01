package kt.example.wxapi.ui

data class ForecastItemViewModel(val degreeDay : String,
                                 val icon : String = "01d",
                                 val date : Long = System.currentTimeMillis(),
                                 val description : String = "No description")