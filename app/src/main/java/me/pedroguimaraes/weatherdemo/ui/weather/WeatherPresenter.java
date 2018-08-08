package me.pedroguimaraes.weatherdemo.ui.weather;

import android.location.Location;
import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import me.pedroguimaraes.weatherdemo.R;
import me.pedroguimaraes.weatherdemo.api.DarkSkyApiInterface;
import me.pedroguimaraes.weatherdemo.interactors.location.LocationGetter;
import me.pedroguimaraes.weatherdemo.model.Currently;
import me.pedroguimaraes.weatherdemo.model.Weather;
import me.pedroguimaraes.weatherdemo.model.WeatherInfo;

public class WeatherPresenter implements WeatherContract.WeatherPresenter {
    private WeatherContract.WeatherView weatherView;

    private CompositeDisposable compositeDisposable;
    private DarkSkyApiInterface darkSkyApiInterface;
    private LocationGetter locationGetter;

    public WeatherPresenter(DarkSkyApiInterface darkSkyApiInterface, LocationGetter locationGetter) {
        this.darkSkyApiInterface = darkSkyApiInterface;
        this.locationGetter = locationGetter;
    }

    @Override
    public void attachView(@NonNull WeatherContract.WeatherView weatherView) {
        this.weatherView = weatherView;
        compositeDisposable = new CompositeDisposable();
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
        if (weatherView != null) {
            weatherView.showProgress();
        }

        Location location = locationGetter.getLocation();

        if (location != null) {
            Disposable disposable = darkSkyApiInterface.getCurrentlyWeather(location.getLatitude(), location.getLongitude())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Weather>() {
                        @Override
                        public void onSuccess(Weather weather) {
                            weatherView.hideProgress();
                            WeatherInfo weatherInfo = weather.getWeatherInfo();
                            weatherView.setCurrentWeather(weatherInfo);
                        }

                        @Override
                        public void onError(Throwable e) {
                            weatherView.showMessage(R.string.weather_data_failure);
                        }
                    });
            compositeDisposable.add(disposable);
        }
    }

}
