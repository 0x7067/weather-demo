package me.pedroguimaraes.weatherdemo.ui.weather;

import me.pedroguimaraes.weatherdemo.model.WeatherInfo;

public interface WeatherContract {

    interface WeatherPresenter {
        void attachView(WeatherView weatherView, WeatherContract.WeatherInteractor weatherInteractor);
        void detachView();
        WeatherView getWeatherView();
    }

    interface WeatherView {
        void showProgress();
        void hideProgress();
        void showMessage(String message);
        void setCurrentWeather(WeatherInfo weatherInfo);
    }

    interface WeatherInteractor {

        interface OnFinishedListener {
            void onFinished(WeatherInfo weatherInfo);
            void onFailure(Throwable t);
        }

        void getWeather(OnFinishedListener onFinishedListener);
    }
}
