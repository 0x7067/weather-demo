package me.pedroguimaraes.weatherdemo.ui.weather;

import me.pedroguimaraes.weatherdemo.model.Weather;

public class WeatherPresenter implements WeatherContract.WeatherPresenter, WeatherContract.WeatherInteractor.OnFinishedListener {
    private WeatherContract.Weatherview weatherView;
    private WeatherContract.WeatherInteractor weatherInteractor;

    @Override
    public void attachView(WeatherContract.Weatherview weatherview, WeatherContract.WeatherInteractor weatherInteractor) {
        this.weatherView = weatherview;
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


    public WeatherContract.Weatherview getWeatherView() {
        return weatherView;
    }

    @Override
    public void onFinished(Weather weather, String city, int icon) {
        weatherView.hideProgress();
        weatherView.setCurrentWeather(weather, city, icon);
    }

    @Override
    public void onFailure(Throwable t) {
        weatherView.showMessage("Failed to get weather data. Please try again later.");
    }
}
