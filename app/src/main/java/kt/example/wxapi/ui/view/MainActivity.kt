package kt.example.wxapi.ui.view

import android.app.Activity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kt.example.wxapi.ErrorTypes
import kt.example.wxapi.R
import kt.example.wxapi.dagger.module.OpenWeatherAPIModule
import kt.example.wxapi.databinding.ActivityMainBinding
import kt.example.wxapi.ui.adapter.ForecastAdapter
import kt.example.wxapi.ui.ForecastItemViewModel
import kt.example.wxapi.ui.presenter.MainPresenter

class MainActivity : AppCompatActivity(), MainView {

    val presenter = MainPresenter(this)

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDI()
        setContentView(R.layout.activity_main)
        initializeForecastList()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        private fun initializeForecastList()
        forecastList.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = ForecastAdapter()
        }
    }

    private fun injectDI(){
        DaggerOpenWeatherAPIComponent
            .builder()
            .openWeatherAPIModule(OpenWeatherAPIModule())
            .build()
            .inject(presenter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu?.findItem(R.id.search_button)
        val searchMenuItem = menuItem?.actionView

        if(searchMenuItem is SearchView){
            searchMenuItem.queryHint = getString(R.string.menu_search_hint)
            searchMenuItem.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean{
                    getForecast(query)
                    menuItem.collapseActionView()
                    return false
                }

                override fun onQueryTextChange(newText: String?):Boolean{
                    return false
                }
            })
        }
        return true
    }

    override fun showSpinner() {
        forecastList.visibility = View.GONE
        emptyStateText.visibility = View.GONE
        loadingSpinner.visibility = View.VISIBLE
    }

    override fun hideSpinner() {
        forecastList.visibility = View.VISIBLE
        loadingSpinner.visibility = View.GONE
    }

    override fun updateForecast(forecasts: List<ForecastItemViewModel>) {
        if(forecasts.isEmpty()) emptyStateView.visibility = View.VISIBLE
        forecastList.adapter.safeCast<ForecastAdapter>()?.addForecast(forecasts)
    }

    private fun getForecast(query: String) = presenter.getForecastForSevenDays(query)

    inline fun <reified T> Any.safeCast() = this as? T

    fun Activity.toast(toastMessage: String, duration: Int = Toast.LENGTH_SHORT){
        Toast.makeText(this, toastMessage, duration).show()
    }
    override fun showErrorToast(errorType: ErrorTypes) {
        when(errorType){
            ErrorTypes.CALL_ERROR -> toast(getString(R.string.connection_error_message))
            ErrorTypes.MISSING_API_KEY -> toast(getString(R.string.missing_api_key_message))
            ErrorTypes.NO_RESULT_FOUND -> toast(getString(R.string.city_not_found_toast_message))
            override fun showSpinner() {
                forecastList.visibility = View.GONE
                emptyStateText.visibility = View.GONE
                loadingSpinner.visibility = View.VISIBLE
            }

            override fun hideSpinner() {
                forecastList.visibility = View.VISIBLE
                loadingSpinner.visibility = View.GONE
            }

            override fun updateForecast(forecasts: List<ForecastItemViewModel>) {
            if(forecasts.isEmpty()) emptyStateView.visibility = View.VISIBLE
            forecastList.adapter.safeCast<ForecastAdapter>()?.addForecast(forecasts)
        }

            private fun getForecast(query: String) = presenter.getForecastForSevenDays(query)

            inline fun <reified T> Any.safeCast() = this as? T

            fun Activity.toast(toastMessage: String, duration: Int = Toast.LENGTH_SHORT){
                Toast.makeText(this, toastMessage, duration).show()
            }
                    override fun showErrorToast(errorType: ErrorTypes) {
                when(errorType){
                    ErrorTypes.CALL_ERROR -> toast(getString(R.string.connection_error_message))
                    ErrorTypes.MISSING_API_KEY -> toast(getString(R.string.missing_api_key_message))
                    ErrorTypes.NO_RESULT_FOUND -> toast(getString(R.string.city_not_found_toast_message))
                }
                loadingSpinner.visibility = View.GONE
                emptyStateText.visibility = View.VISIBLE
            }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}