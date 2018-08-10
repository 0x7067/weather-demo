package me.pedroguimaraes.weatherdemo;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;

public class WeatherApplication extends Application {

    private static WeatherApplication instance;

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        startLeakCanary();
    }

    private void startLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
