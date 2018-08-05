package me.pedroguimaraes.weatherdemo.ui.weather

import me.pedroguimaraes.weatherdemo.model.WeatherInfo

interface WeatherContract {

    interface WeatherPresenter {
        val weatherView: WeatherView
        fun attachView(weatherView: WeatherView, weatherInteractor: WeatherContract.WeatherInteractor)
        fun detachView()
    }

    interface WeatherView {
        fun showProgress()
        fun hideProgress()
        fun showMessage(message: String)
        fun setCurrentWeather(weatherInfo: WeatherInfo)
    }

    interface WeatherInteractor {

        interface OnFinishedListener {
            fun onFinished(weatherInfo: WeatherInfo)
            fun onFailure(t: Throwable)
        }

        fun getWeather(onFinishedListener: OnFinishedListener)
    }
}
