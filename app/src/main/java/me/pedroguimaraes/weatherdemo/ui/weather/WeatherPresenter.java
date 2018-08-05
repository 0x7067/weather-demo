package me.pedroguimaraes.weatherdemo.ui.weather;

import android.location.Location;
import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import me.pedroguimaraes.weatherdemo.api.DarkSkyApiInterface;
import me.pedroguimaraes.weatherdemo.interactors.location.LocationManager;
import me.pedroguimaraes.weatherdemo.model.Currently;
import me.pedroguimaraes.weatherdemo.model.Weather;
import me.pedroguimaraes.weatherdemo.model.WeatherInfo;

public class WeatherPresenter implements WeatherContract.WeatherPresenter {
    private WeatherContract.WeatherView weatherView;

    private CompositeDisposable compositeDisposable;
    private DarkSkyApiInterface darkSkyApiInterface;
    private LocationManager locationManager;

    public WeatherPresenter() {
        this.compositeDisposable = new CompositeDisposable();
        this.darkSkyApiInterface = DarkSkyApiInterface.Companion.create();
        this.locationManager = new LocationManager();
    }


    @Override
    public void attachView(@NonNull WeatherContract.WeatherView weatherView) {
        this.weatherView = weatherView;
    }

    public void onResume() {
        if (weatherView != null) {
            weatherView.showProgress();
        }

        getWeatherData();
    }

    @Override
    public void detachView() {
        weatherView = null;
        compositeDisposable.dispose();
    }


    public WeatherContract.WeatherView getWeatherView() {
        return weatherView;
    }

    @Override
    public void getWeatherData() {
        locationManager = new LocationManager();
        compositeDisposable = new CompositeDisposable();

        Location location = locationManager.getLocation();
        if (location != null) {
            Disposable disposable = darkSkyApiInterface.getCurrentlyWeather(location.getLatitude(), location.getLongitude())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Weather>() {
                        @Override
                        public void onSuccess(Weather weather) {
                            weatherView.hideProgress();
                            WeatherInfo weatherInfo = getWeatherInfo(weather);
                            weatherView.setCurrentWeather(weatherInfo);
                        }

                        @Override
                        public void onError(Throwable e) {
                            weatherView.showMessage("Failed to get weather data. Please try again later.");
                        }
                    });
            compositeDisposable.add(disposable);
        }
    }

    private WeatherInfo getWeatherInfo(Weather weather) {
        Currently currently = weather.getCurrently();
        Double lat = weather.getLatitude();
        Double lon = weather.getLongitude();
        String icon = currently.getIcon();
        String summary = weather.getCurrently().getSummary();
        Double temperature = weather.getCurrently().getTemperature();
        Double rainProbability = weather.getCurrently().getPrecipProbability();
        Double windSpeed = weather.getCurrently().getWindSpeed();

        return new WeatherInfo(lat, lon, icon, summary, temperature, rainProbability, windSpeed);
    }

}
