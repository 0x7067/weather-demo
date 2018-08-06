package me.pedroguimaraes.weatherdemo.ui.weather

import android.location.Location
import me.pedroguimaraes.weatherdemo.model.WeatherInfo

interface WeatherContract {

    interface WeatherPresenter {
        val weatherView: WeatherView?
        fun attachView(weatherView: WeatherView)
        fun detachView()
        fun getWeatherData(location: Location)
    }

    interface WeatherView {
        fun showProgress()
        fun hideProgress()
        fun showMessage(message: Int)
        fun setCurrentWeather(weatherInfo: WeatherInfo)
    }
}
