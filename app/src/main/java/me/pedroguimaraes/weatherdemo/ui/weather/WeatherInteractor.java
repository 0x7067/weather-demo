package me.pedroguimaraes.weatherdemo.ui.weather;

import android.location.Location;

import java.util.HashMap;

import me.pedroguimaraes.weatherdemo.R;
import me.pedroguimaraes.weatherdemo.api.DarkSkyApiInterface;
import me.pedroguimaraes.weatherdemo.interactors.location.LocationManager;
import me.pedroguimaraes.weatherdemo.model.Currently;
import me.pedroguimaraes.weatherdemo.model.Weather;
import me.pedroguimaraes.weatherdemo.model.WeatherInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherInteractor implements WeatherContract.WeatherInteractor {

    private DarkSkyApiInterface darkSkyApiInterface;
    private LocationManager locationManager;

    WeatherInteractor() {
        this.darkSkyApiInterface = DarkSkyApiInterface.Companion.create();
        this.locationManager = new LocationManager();
    }

    @Override
    public void getWeather(OnFinishedListener onFinishedListener) {
        Location location = locationManager.getLocation();
        darkSkyApiInterface.getCurrentlyWeather(location.getLatitude(), location.getLongitude()).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                WeatherInfo weatherInfo = getWeatherInfo(response.body());
                onFinishedListener.onFinished(weatherInfo);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    private WeatherInfo getWeatherInfo(Weather weather) {
        Currently currently = weather.getCurrently();
        String cityName = getCityName(weather.getLatitude(), weather.getLongitude());
        int icon = getWeatherIcon(currently.getIcon());
        String summary = weather.getCurrently().getSummary();
        Double temperature = weather.getCurrently().getTemperature();
        Double rainProbability = weather.getCurrently().getPrecipProbability();
        Double windSpeed = weather.getCurrently().getWindSpeed();

        return new WeatherInfo(cityName, icon, summary, temperature, rainProbability, windSpeed);
    }

    private String getCityName(Double lat, Double lon) {
        return locationManager.getCityName(lat, lon);
    }

    private int getWeatherIcon(String icon) {
        HashMap<String, Integer> weather = getWeatherMap();
        return weather.get(icon);
    }

    private HashMap<String, Integer> getWeatherMap() {
        HashMap<String, Integer> weather = new HashMap<>();

        weather.put("clear-day", R.mipmap.clear_day);
        weather.put("clear-night", R.mipmap.clear_night);
        weather.put("rain", R.mipmap.rain);
        weather.put("snow", R.mipmap.snow);
        weather.put("sleet", R.mipmap.none_available);
        weather.put("wind", R.mipmap.wind);
        weather.put("fog", R.mipmap.fog);
        weather.put("cloudy", R.mipmap.cloudy);
        weather.put("partly-cloudy-day", R.mipmap.partly_cloudy_day);
        weather.put("partly-cloudy-night", R.mipmap.partly_cloudy_night);
        weather.put("hail", R.mipmap.hail);
        weather.put("thunderstorm", R.mipmap.thunderstorm);
        weather.put("tornado", R.mipmap.none_available);

        return weather;
    }
}
