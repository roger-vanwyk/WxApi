package kt.example.wxapi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kt.example.wxapi.R
import kt.example.wxapi.ui.ForecastItemViewModel
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    var forecastList = mutableListOf<ForecastItemViewModel>()

    fun addForecast(list: List<ForecastItemViewModel>) {
        forecastList.clear()
        forecastList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.forecast_list_item, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        forecastList[position].let {
            holder.bind(forecastElement = it)
        }
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(forecastElement: ForecastItemViewModel) {
//            itemView.degreeText.text =
//                "${forecastElement.degreeDay} °C ${forecastElement.description}"
//            itemView.detailDateText.text = getDate(forecastElement.date)
//            Glide.with(itemView.context)
//                .load("http://openweatermap.org/img/w/${forecastElement.icon}.png")
//                .into(itemView.weatherIcon)
            itemView.setOnClickListener { openDetailsFragment(itemView.context, forecastElement) }
        }

        private fun openDetailsFragment(context: Context?, forecastElement: ForecastItemViewModel) {
            findNavController().navigate(R.id.action_WeatherFragment_to_DetailsFragment)
        }

        private fun findNavController(): NavController {
            TODO("Not yet implemented")
        }

        private fun getDate(date: Long): String {
            val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
            return timeFormatter.format(Date(date * 1000L))
        }
    }
}