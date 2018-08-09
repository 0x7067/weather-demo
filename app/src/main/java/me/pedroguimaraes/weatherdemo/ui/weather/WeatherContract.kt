package me.pedroguimaraes.weatherdemo.ui.weather

import me.pedroguimaraes.weatherdemo.model.WeatherInfo

interface WeatherContract {

    interface WeatherPresenter {
        var weatherView: WeatherView?
        fun attachView(weatherView: WeatherView)
        fun detachView()
        fun getWeatherData()
    }

    interface WeatherView {
        fun showProgress()
        fun hideProgress()
        fun showMessage(message: Int)
        fun setCurrentWeather(weatherInfo: WeatherInfo)
    }
}
