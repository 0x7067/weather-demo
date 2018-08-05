package me.pedroguimaraes.weatherdemo.ui.weather;

import me.pedroguimaraes.weatherdemo.model.WeatherInfo;

public class WeatherPresenter implements WeatherContract.WeatherPresenter, WeatherContract.WeatherInteractor.OnFinishedListener {
    private WeatherContract.WeatherView weatherView;
    private WeatherContract.WeatherInteractor weatherInteractor;

    @Override
    public void attachView(WeatherContract.WeatherView weatherView, WeatherContract.WeatherInteractor weatherInteractor) {
        this.weatherView = weatherView;
        this.weatherInteractor = weatherInteractor;
    }

    public void onResume() {
        if (weatherView != null) {
            weatherView.showProgress();
        }

        getCurrentWeather();
    }


    @Override
    public void detachView() {
        weatherView = null;
    }

    private void getCurrentWeather() {
        weatherInteractor.getWeather(this);
    }


    public WeatherContract.WeatherView getWeatherView() {
        return weatherView;
    }

    @Override
    public void onFinished(WeatherInfo weatherInfo) {
        weatherView.hideProgress();
        weatherView.setCurrentWeather(weatherInfo);
    }

    @Override
    public void onFailure(Throwable t) {
        weatherView.showMessage("Failed to get weather data. Please try again later.");
    }
}
