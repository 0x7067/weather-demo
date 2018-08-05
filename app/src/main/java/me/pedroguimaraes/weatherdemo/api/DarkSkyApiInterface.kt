package me.pedroguimaraes.weatherdemo.api

import io.reactivex.Single
import me.pedroguimaraes.weatherdemo.model.Weather
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface DarkSkyApiInterface {

	@GET("{lat},{lng}/?units=si")
	fun getCurrentlyWeather(@Path("lat") lat: Double, @Path("lng") lng: Double): Single<Weather>

	companion object {
		fun create(): DarkSkyApiInterface {

			val retrofit = Retrofit.Builder()
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.baseUrl("https://api.darksky.net/forecast/ab3df38db111c4ac834b442f32cde995/")
					.build()

			return retrofit.create(DarkSkyApiInterface::class.java)
		}
	}
}
