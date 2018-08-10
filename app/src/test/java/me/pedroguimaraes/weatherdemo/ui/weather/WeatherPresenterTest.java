package me.pedroguimaraes.weatherdemo.ui.weather;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import me.pedroguimaraes.weatherdemo.api.DarkSkyApiInterface;
import me.pedroguimaraes.weatherdemo.interactors.location.LocationGetter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {

    @Mock
    DarkSkyApiInterface darkSkyApiInterface;

    @Mock
    LocationGetter locationGetter;

    @Test
    public void testAttach() {
        WeatherPresenter weatherPresenter = new WeatherPresenter(darkSkyApiInterface, locationGetter);
        assertNull(weatherPresenter.getWeatherView());

        weatherPresenter.attachView(mock(WeatherContract.WeatherView.class));
        assertNotNull(weatherPresenter.getWeatherView());
    }

    @Test
    public void testDetach() {
        WeatherPresenter weatherPresenter = new WeatherPresenter(darkSkyApiInterface, locationGetter);
        weatherPresenter.attachView(mock(WeatherContract.WeatherView.class));
        assertNotNull(weatherPresenter.getWeatherView());

        weatherPresenter.detachView();
        assertNull(weatherPresenter.getWeatherView());
    }

}