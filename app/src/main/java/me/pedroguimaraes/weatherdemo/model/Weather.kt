package me.pedroguimaraes.weatherdemo.model

data class Weather(val latitude: Double, val longitude: Double, val timezone: String,
				   val offset: Int, val currently: Currently)
