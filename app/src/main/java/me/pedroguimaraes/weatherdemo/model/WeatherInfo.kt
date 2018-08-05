package me.pedroguimaraes.weatherdemo.model

import java.util.Locale

class WeatherInfo(val cityName: String, val icon: Int, val summary: String,
                  private val temperature: Double?, private val rainProbability: Double?,
                  private val windSpeed: Double?) {

    fun getTemperature(): String {
        return String.format(Locale.getDefault(), "%.0f", temperature) + "°"
    }

    fun getRainProbability(): String {
        return String.format(Locale.getDefault(), "%.0f", rainProbability) + "%"
    }

    fun getWindSpeed(): String {
        return String.format(Locale.getDefault(), "%.0f", windSpeed) + " mph"
    }
}
