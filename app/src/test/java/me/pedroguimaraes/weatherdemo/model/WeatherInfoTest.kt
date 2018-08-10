package me.pedroguimaraes.weatherdemo.model

import me.pedroguimaraes.weatherdemo.R
import kotlin.test.assertEquals
import org.junit.Test as test

class WeatherInfoTest {

    private val weatherInfo = WeatherInfo(37.8267, -122.4233, "partly-cloudy-day",
            "Partly Cloudy", 65.12, 1.3, 6.98)

    @test
    fun formatTemperature() {
        assertEquals("65°", weatherInfo.getTemperature())
    }

    @test
    fun formatRainProbability() {
        assertEquals("1%", weatherInfo.getRainProbability())
    }

    @test
    fun formatWindSpeed() {
        assertEquals("7 mph", weatherInfo.getWindSpeed())
    }

    @test
    fun getsCorrectIcon() {
        assertEquals(R.mipmap.partly_cloudy_day, weatherInfo.getWeatherIcon())
    }

    @test
    fun getsDefaultIconIfNotAvailable() {
        val weatherInfo = WeatherInfo(37.8267, -122.4233, "gibberish",
                "Partly Cloudy", 65.12, 1.3, 6.98)

        assertEquals(R.mipmap.none_available, weatherInfo.getWeatherIcon())
    }
}

