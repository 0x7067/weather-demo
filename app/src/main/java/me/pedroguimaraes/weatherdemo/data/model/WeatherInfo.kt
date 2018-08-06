package me.pedroguimaraes.weatherdemo.data.model


import me.pedroguimaraes.weatherdemo.R
import me.pedroguimaraes.weatherdemo.interactors.location.LocationGetter
import java.util.*

class WeatherInfo   (private val lat: Double, private val lon: Double, val icon: String,
                  val summary: String, private val temperature: Double?,
                  private val rainProbability: Double?, private val windSpeed: Double?) {

    fun getTemperature(): String {
        return String.format(Locale.getDefault(), "%.0f", temperature) + "°"
    }

    fun getRainProbability(): String {
        return String.format(Locale.getDefault(), "%.0f", rainProbability) + "%"
    }

    fun getWindSpeed(): String {
        return String.format(Locale.getDefault(), "%.0f", windSpeed) + " mph"
    }

    fun getCityName(): String {
        val locationManager = LocationGetter()
        return locationManager.getCityName(lat, lon)
    }

    fun getWeatherIcon(): Int? {
        val weather = getWeatherMap()
        return getOrDefault(weather, icon, R.mipmap.none_available)
    }

    private fun getWeatherMap(): HashMap<String, Int> {
        val weather = HashMap<String, Int>()

        weather["clear-day"] = R.mipmap.clear_day
        weather["clear-night"] = R.mipmap.clear_night
        weather["rain"] = R.mipmap.rain
        weather["snow"] = R.mipmap.snow
        weather["sleet"] = R.mipmap.none_available
        weather["wind"] = R.mipmap.wind
        weather["fog"] = R.mipmap.fog
        weather["cloudy"] = R.mipmap.cloudy
        weather["partly-cloudy-day"] = R.mipmap.partly_cloudy_day
        weather["partly-cloudy-night"] = R.mipmap.partly_cloudy_night
        weather["hail"] = R.mipmap.hail
        weather["thunderstorm"] = R.mipmap.thunderstorm
        weather["tornado"] = R.mipmap.none_available

        return weather
    }

    private fun <K, V> getOrDefault(map: Map<K, V>, key: K, defaultValue: V): V? {
        return if ((map[key]) != null || map.containsKey(key))
            map[key]
        else
            defaultValue
    }

}
