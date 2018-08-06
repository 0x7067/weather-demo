package me.pedroguimaraes.weatherdemo;

import javax.inject.Singleton;

import dagger.Component;
import me.pedroguimaraes.weatherdemo.injection.ApiModule;
import me.pedroguimaraes.weatherdemo.injection.AppModule;
import me.pedroguimaraes.weatherdemo.ui.weather.WeatherActivity;

@Singleton
@Component(modules={AppModule.class, ApiModule.class})
public interface AppComponent {
    void inject(WeatherActivity activity);
}
