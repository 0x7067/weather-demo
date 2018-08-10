package me.pedroguimaraes.weatherdemo.ui.weather

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import me.pedroguimaraes.weatherdemo.R
import me.pedroguimaraes.weatherdemo.api.DarkSkyApiInterface
import me.pedroguimaraes.weatherdemo.interactors.location.LocationGetter
import me.pedroguimaraes.weatherdemo.model.Weather

class WeatherPresenter(private val darkSkyApiInterface: DarkSkyApiInterface,
        private val locationGetter: LocationGetter) : WeatherContract.WeatherPresenter {

    override var weatherView: WeatherContract.WeatherView? = null
    private var compositeDisposable = CompositeDisposable()

    override fun attachView(weatherView: WeatherContract.WeatherView) {
        this.weatherView = weatherView
    }

    override fun detachView() {
        this.weatherView = null
        compositeDisposable.dispose()
    }

    override fun getWeatherData() {
        weatherView?.showProgress()

        val location = locationGetter.getLocation()

        if (location != null) {
            val disposable = darkSkyApiInterface.getCurrentlyWeather(location.latitude, location.longitude)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Weather>() {
                        override fun onSuccess(weather: Weather) {
                            weatherView!!.hideProgress()
                            val weatherInfo = weather.getWeatherInfo()
                            weatherView!!.setCurrentWeather(weatherInfo)
                        }

                        override fun onError(e: Throwable) {
                            weatherView!!.showMessage(R.string.weather_data_failure)
                        }
                    })
            compositeDisposable.add(disposable)
        }
    }
}
