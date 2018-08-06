package me.pedroguimaraes.weatherdemo.injection

import dagger.Module
import dagger.Provides
import me.pedroguimaraes.weatherdemo.Config
import me.pedroguimaraes.weatherdemo.data.api.DarkSkyApiInterface
import me.pedroguimaraes.weatherdemo.interactors.location.LocationGetter
import me.pedroguimaraes.weatherdemo.ui.weather.WeatherPresenter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Named(BASE_URL)
    fun provideBaseUrl(): String {
        return Config.BASE_URL
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(@Named(BASE_URL) baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    @Provides
    @Singleton
    fun providesWeatherApi(retrofit: Retrofit): DarkSkyApiInterface {
        return retrofit.create(DarkSkyApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun LocationGetter(): LocationGetter {
        return LocationGetter()
    }

    @Provides
    @Singleton
    fun WeatherPresenter(): WeatherPresenter {
        return WeatherPresenter(providesWeatherApi(providesRetrofit(Config.BASE_URL, providesHttpClient(providesHttpLoggingInterceptor()))), LocationGetter())
    }

    companion object {
        private const val BASE_URL = "base_url"
    }
}
