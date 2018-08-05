package me.pedroguimaraes.weatherdemo.ui.weather;

import me.pedroguimaraes.weatherdemo.model.Weather;

public interface WeatherContract {

    interface WeatherPresenter {
        void attachView(WeatherContract.Weatherview weatherview, WeatherContract.WeatherInteractor weatherInteractor);
        void detachView();
        Weatherview getWeatherView();
    }

    interface Weatherview {
        void showProgress();
        void hideProgress();
        void showMessage(String message);
        void setCurrentWeather(Weather weather, String city, int icon);
    }

    interface WeatherInteractor {

        interface OnFinishedListener {
            void onFinished(Weather weather, String cityName, int icon);
            void onFailure(Throwable t);
        }

        void getWeather(OnFinishedListener onFinishedListener);
    }
}
