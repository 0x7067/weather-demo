package me.pedroguimaraes.weatherdemo.ui.weather

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_weather_today.*
import me.pedroguimaraes.weatherdemo.R
import me.pedroguimaraes.weatherdemo.injection.DependencyInjection
import me.pedroguimaraes.weatherdemo.model.WeatherInfo

class WeatherActivity : AppCompatActivity(), WeatherContract.WeatherView {

    private lateinit var weatherPresenter: WeatherPresenter
    private lateinit var rxPermissions: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dependencyInjection = DependencyInjection(this)

        rxPermissions = RxPermissions(this)

        weatherPresenter = dependencyInjection.weatherPresenter()
        weatherPresenter.attachView(this)


        srl_weather.setOnRefreshListener { withLocationPermission { weatherPresenter.getWeatherData() } }

        withLocationPermission { weatherPresenter.getWeatherData() }
    }


    override fun showProgress() {
        srl_weather.isRefreshing = true
    }

    override fun hideProgress() {
        srl_weather.isRefreshing = false
    }

    override fun showMessage(message: Int) {
        hideProgress()
        Snackbar.make(srl_weather, message, Snackbar.LENGTH_LONG).show()
    }

    override fun setCurrentWeather(weatherInfo: WeatherInfo) {
        tv_city_temperature.text = weatherInfo.getTemperature()
        tv_summary.text = weatherInfo.summary
        tv_weather_rain.text = weatherInfo.getRainProbability()
        tv_weather_wind.text = weatherInfo.getWindSpeed()
        tv_city_name.text = weatherInfo.getCityName()
        img_weather_icon.setImageResource(weatherInfo.getWeatherIcon()!!)
    }

    override fun onDestroy() {
        weatherPresenter.detachView()
        super.onDestroy()
    }

    private fun withLocationPermission(callback: () -> Unit) {
        this.let { activity ->
            RxPermissions(activity)
                    .requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe { permission ->
                        when {
                            permission.granted -> callback()
                            permission.shouldShowRequestPermissionRationale -> {
                                showPermissionRationale(R.string.permission_location_rationale) { weatherPresenter.getWeatherData() }
                            }
                            else -> {
                                showPermissionRationale(R.string.permission_location_rationale) { goToSettings() }
                            }
                        }

                    }
        }
    }

    private fun showPermissionRationale(permissionMessage: Int, method: () -> Unit) {
        val dialogBuilder = AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(permissionMessage)
                .setPositiveButton(android.R.string.ok) { _, _ -> withLocationPermission { method() } }
                .setNegativeButton(android.R.string.no) { _, _ -> }

        dialogBuilder.create().show()
    }

    private fun goToSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
