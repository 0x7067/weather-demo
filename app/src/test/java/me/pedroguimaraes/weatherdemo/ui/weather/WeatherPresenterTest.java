package me.pedroguimaraes.weatherdemo.ui.weather;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {

    @Test
    public void testAttach() {
        WeatherPresenter weatherPresenter = new WeatherPresenter();
        assertNull(weatherPresenter.getWeatherView());


        weatherPresenter.attachView(mock(WeatherContract.WeatherView.class));
        assertNotNull(weatherPresenter.getWeatherView());
    }

    @Test
    public void testDetach() {
        WeatherPresenter weatherPresenter = new WeatherPresenter();
        weatherPresenter.attachView(mock(WeatherContract.WeatherView.class));
        assertNotNull(weatherPresenter.getWeatherView());

        weatherPresenter.detachView();
        assertNull(weatherPresenter.getWeatherView());
    }

}