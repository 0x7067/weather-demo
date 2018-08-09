package me.pedroguimaraes.weatherdemo.model

data class Weather(val latitude: Double, val longitude: Double, val timezone: String,
				   val offset: Int, val currentWeather: CurrentWeather) {

    fun getWeatherInfo(): WeatherInfo {
        val lat = this.latitude
        val lon = this.longitude
        val icon = this.currentWeather.icon
        val summary = this.currentWeather.summary
        val temperature = this.currentWeather.temperature
        val rainProbability = this.currentWeather.precipProbability
        val windSpeed = this.currentWeather.windSpeed

        return WeatherInfo(lat, lon, icon, summary, temperature, rainProbability, windSpeed)
    }
}