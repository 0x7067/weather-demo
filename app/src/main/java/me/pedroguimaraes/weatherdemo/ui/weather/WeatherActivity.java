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
import me.pedroguimaraes.weatherdemo.model.WeatherInfo;

public class WeatherActivity extends AppCompatActivity implements WeatherContract.WeatherView {

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

        weatherPresenter = new WeatherPresenter();
        weatherPresenter.attachView(this, new WeatherInteractor());
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
    public void setCurrentWeather(WeatherInfo weatherInfo) {
        cityTemperature.setText(weatherInfo.getTemperature());
        citySummary.setText(weatherInfo.getSummary());
        rainProbability.setText(weatherInfo.getRainProbability());
        windSpeed.setText(weatherInfo.getWindSpeed());
        cityName.setText(weatherInfo.getCityName());
        weatherIcon.setImageResource(weatherInfo.getIcon());
    }

    @Override
    protected void onDestroy() {
        weatherPresenter.detachView();
        super.onDestroy();
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
