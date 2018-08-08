package me.pedroguimaraes.weatherdemo.model

data class Weather(val latitude: Double, val longitude: Double, val timezone: String,
				   val offset: Int, val currently: Currently) {

    fun getWeatherInfo(): WeatherInfo {
        val lat = this.latitude
        val lon = this.longitude
        val icon = this.currently.icon
        val summary = this.currently.summary
        val temperature = this.currently.temperature
        val rainProbability = this.currently.precipProbability
        val windSpeed = this.currently.windSpeed

        return WeatherInfo(lat, lon, icon, summary, temperature, rainProbability, windSpeed)
    }
}