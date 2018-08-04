package me.pedroguimaraes.weatherdemo.ui.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.pedroguimaraes.weatherdemo.R;
import me.pedroguimaraes.weatherdemo.interactors.permissions.PermissionEnforcer;
import me.pedroguimaraes.weatherdemo.model.Currently;
import me.pedroguimaraes.weatherdemo.model.Weather;
import me.pedroguimaraes.weatherdemo.util.TemperatureUnit;

public class WeatherActivity extends AppCompatActivity implements WeatherContract.Weatherview {

    private WeatherPresenter weatherPresenter;
    private PermissionEnforcer permissionEnforcer;

    @BindView(R.id.srl_weather)
    SwipeRefreshLayout swipeRefreshWeather;

    @BindView(R.id.tv_city_temperature)
    TextView cityTemperature;

    @BindView(R.id.tv_summary)
    TextView citySummary;

    @BindView(R.id.tv_weather_rain)
    TextView rainProbability;

    @BindView(R.id.tv_weather_wind)
    TextView windSpeed;

    @BindView(R.id.tv_city_name)
    TextView cityName;

    @BindView(R.id.img_weather_icon)
    ImageView weatherIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionEnforcer = new PermissionEnforcer(this);
        permissionEnforcer.requestPermissions();

        weatherPresenter = new WeatherPresenter(this, new WeatherInteractor());
        ButterKnife.bind(this);


        swipeRefreshWeather.setOnRefreshListener(this::getCurrentWeather);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentWeather();
    }

    private void getCurrentWeather() {
        if (permissionEnforcer.allPermissionsGranted()) {
            weatherPresenter.onResume();
        }
    }

    @Override
    public void showProgress() {
        swipeRefreshWeather.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshWeather.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        hideProgress();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setCurrentWeather(Weather weather, String city, int icon) {
        Currently currently = weather.getCurrently();
        cityTemperature.setText(String.format("%.0f", TemperatureUnit.fahrenheitToCelsius(currently.getTemperature())) + "°");
        citySummary.setText(currently.getSummary());
        rainProbability.setText(String.format("%.0f", currently.getPrecipProbability()) + " %");
        windSpeed.setText(String.format("%.0f", currently.getWindSpeed()) + "mph");
        cityName.setText(city);
        weatherIcon.setImageResource(icon);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherPresenter.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionEnforcer.enforcePermissions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PermissionEnforcer.APP_SETTINGS_REQUEST_CODE) {
            permissionEnforcer.enforcePermissions();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
