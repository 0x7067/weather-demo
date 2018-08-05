package me.pedroguimaraes.weatherdemo.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TemperatureUnitTest {

    @Test
    public void fahrenheitToCelsius() {
        assertThat(TemperatureUnit.fahrenheitToCelsius(32.0), is(0.0));
    }

    @Test
    public void celsiusToFahrenheit() {
        assertThat(TemperatureUnit.celsiusToFahrenheit(0.0), is(32.0));
    }
}