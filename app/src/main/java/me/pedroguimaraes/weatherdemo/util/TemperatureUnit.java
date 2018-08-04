package me.pedroguimaraes.weatherdemo.util;

public class TemperatureUnit {

    public static Double fahrenheitToCelsius(Double temperature) {
        return (5.0 / 9.0) * (temperature - 32);
    }

    public static Double celsiusToFahrenheit(Double temperature) {
        return (9.0 / 5.0) * temperature + 32;
    }
}
