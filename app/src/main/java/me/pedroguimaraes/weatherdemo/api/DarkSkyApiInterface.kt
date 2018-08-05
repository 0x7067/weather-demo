package me.pedroguimaraes.weatherdemo.api

import io.reactivex.Single
import me.pedroguimaraes.weatherdemo.model.Weather
import retrofit2.http.GET
import retrofit2.http.Path


interface DarkSkyApiInterface {

	@GET("{lat},{lng}/?units=si")
	fun getCurrentlyWeather(@Path("lat") lat: Double, @Path("lng") lng: Double): Single<Weather>

}
