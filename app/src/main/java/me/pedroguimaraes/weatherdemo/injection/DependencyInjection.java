package me.pedroguimaraes.weatherdemo.injection;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.LocationManager;
import java.util.Locale;
import me.pedroguimaraes.weatherdemo.api.DarkSkyApiInterface;
import me.pedroguimaraes.weatherdemo.interactors.location.LocationGetter;
import me.pedroguimaraes.weatherdemo.ui.weather.WeatherPresenter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DependencyInjection {

    public static final String BASE_URL = "https://api.darksky.net/forecast/ab3df38db111c4ac834b442f32cde995/";
    private Context context;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private DarkSkyApiInterface weatherApi;

    public DependencyInjection(Context context) {
        this.context = context;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(BODY);

            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
        }

        return okHttpClient;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public DarkSkyApiInterface getWeatherApi() {
        if (weatherApi == null) {
            weatherApi = getRetrofit()
                    .create(DarkSkyApiInterface.class);
        }
        return weatherApi;
    }

    public LocationManager locationManager() {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public Geocoder geocoder() {
        return new Geocoder(context, Locale.getDefault());
    }

    public PackageManager packageManager() {
        return context.getPackageManager();
    }

    public LocationGetter locationGetter() {
        return new LocationGetter();
    }

    public WeatherPresenter weatherPresenter() {
        return new WeatherPresenter(getWeatherApi(), locationGetter());
    }

}

