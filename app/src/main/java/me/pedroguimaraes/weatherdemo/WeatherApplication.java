package me.pedroguimaraes.weatherdemo;

import android.app.Application;
import android.content.Context;

public class WeatherApplication extends Application {

    private static WeatherApplication instance;

    public static Context getContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
