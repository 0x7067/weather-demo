package me.pedroguimaraes.weatherdemo.data.model

data class Weather(val latitude: Double, val longitude: Double, val timezone: String,
				   val offset: Int, val currently: Currently)
