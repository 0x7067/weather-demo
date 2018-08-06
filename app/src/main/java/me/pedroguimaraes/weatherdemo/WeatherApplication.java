package me.pedroguimaraes.weatherdemo;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

import me.pedroguimaraes.weatherdemo.injection.ApiModule;
import me.pedroguimaraes.weatherdemo.injection.AppModule;

public class WeatherApplication extends Application {

    private static WeatherApplication instance;

    private AppComponent appComponent;

    public static Context getContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        startLeakCanary();

        appComponent = DaggerAppComponent.builder()
                .applicationModule(new AppModule(this))
                .apiModule(new ApiModule())
                .build();
    }

    public AppComponent getComponent() {
        return this.appComponent;
    }

    private void startLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
