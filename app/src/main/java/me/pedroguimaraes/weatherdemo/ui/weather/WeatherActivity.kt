package me.pedroguimaraes.weatherdemo.ui.weather

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_weather_today.*
import me.pedroguimaraes.weatherdemo.R
import me.pedroguimaraes.weatherdemo.interactors.permissions.PermissionEnforcer
import me.pedroguimaraes.weatherdemo.model.WeatherInfo

class WeatherActivity : AppCompatActivity(), WeatherContract.WeatherView {

    lateinit var weatherPresenter: WeatherPresenter
    lateinit var permissionEnforcer: PermissionEnforcer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionEnforcer = PermissionEnforcer(this)
        permissionEnforcer.requestPermissions()

        weatherPresenter = WeatherPresenter()
        weatherPresenter.attachView(this)


        srl_weather.setOnRefreshListener({ this.getCurrentWeather() })
    }

    override fun onResume() {
        super.onResume()
        getCurrentWeather()
    }

    private fun getCurrentWeather() {
        if (permissionEnforcer.allPermissionsGranted()) {
            weatherPresenter.onResume()
        }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionEnforcer.enforcePermissions()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == PermissionEnforcer.APP_SETTINGS_REQUEST_CODE) {
            permissionEnforcer.enforcePermissions()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
