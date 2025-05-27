package com.yablonskyi.caelum.ui.weather.list.preview

import com.yablonskyi.caelum.domain.model.City
import com.yablonskyi.caelum.domain.model.Clouds
import com.yablonskyi.caelum.domain.model.HourForecast
import com.yablonskyi.caelum.domain.model.Main
import com.yablonskyi.caelum.domain.model.Weather
import com.yablonskyi.caelum.domain.model.Wind
import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day.CityCoordState
import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day.CurrentForecastState
import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day.ForecastBundle
import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.hour.DayForecastState

val cityListPreview = listOf(
    City(name = "Odesa", lat = 46.4843023, lon = 30.7322878),
    City(name = "Kyiv", lat = 50.4501, lon = 30.5234),
    City(name = "Lviv", lat = 49.8397, lon = 24.0297),
    City(name = "Dnipro", lat = 48.4647, lon = 35.0462),
    City(name = "Kharkiv", lat = 49.9935, lon = 36.2304)
)

val forecastListPreview = listOf(
    ForecastBundle(
        cityState = CityCoordState(),
        currentForecastState = CurrentForecastState(
            forecast = HourForecast(
                main = Main(temp = 20.0, feelsLike = 19.0, pressure = 1012, humidity = 65),
                weather = listOf(Weather("Clear", "clear sky")),
                wind = Wind(speed = 5.0, deg = 180),
                clouds = Clouds(all = 10),
                visibility = 10000,
                sunrise = 1683955200L,
                sunset = 1684005600L,
                time = 1748347632L,
                timezone = 3600
            )
        ),
        dayForecastState = DayForecastState()
    ),
    ForecastBundle(
        cityState = CityCoordState(),
        currentForecastState = CurrentForecastState(
            forecast = HourForecast(
                main = Main(temp = 25.0, feelsLike = 24.0, pressure = 1010, humidity = 70),
                weather = listOf(Weather("Clouds", "scattered clouds")),
                wind = Wind(speed = 3.0, deg = 90),
                clouds = Clouds(all = 40),
                visibility = 9000,
                sunrise = 1683955400L,
                sunset = 1684005800L,
                time = 1748347632L,
                timezone = 3600
            )
        ),
        dayForecastState = DayForecastState()
    ),
    ForecastBundle(
        cityState = CityCoordState(),
        currentForecastState = CurrentForecastState(
            forecast = HourForecast(
                main = Main(temp = 15.0, feelsLike = 13.5, pressure = 1018, humidity = 55),
                weather = listOf(Weather("Rain", "light rain")),
                wind = Wind(speed = 6.0, deg = 270),
                clouds = Clouds(all = 80),
                visibility = 7000,
                sunrise = 1683955600L,
                sunset = 1684006000L,
                time = 1748347632L,
                timezone = 3600
            )
        ),
        dayForecastState = DayForecastState()
    ),
    ForecastBundle(
        cityState = CityCoordState(),
        currentForecastState = CurrentForecastState(
            forecast = HourForecast(
                main = Main(temp = 10.0, feelsLike = 9.0, pressure = 1020, humidity = 50),
                weather = listOf(Weather("Snow", "light snow")),
                wind = Wind(speed = 4.0, deg = 60),
                clouds = Clouds(all = 90),
                visibility = 3000,
                sunrise = 1683955800L,
                sunset = 1684006200L,
                time = 1748347632L,
                timezone = 3600
            )
        ),
        dayForecastState = DayForecastState()
    ),
    ForecastBundle(
        cityState = CityCoordState(),
        currentForecastState = CurrentForecastState(
            forecast = HourForecast(
                main = Main(temp = 30.0, feelsLike = 32.0, pressure = 1005, humidity = 40),
                weather = listOf(Weather("Sunny", "sunny day")),
                wind = Wind(speed = 2.0, deg = 10),
                clouds = Clouds(all = 5),
                visibility = 10000,
                sunrise = 1683956000L,
                sunset = 1684006400L,
                time = 1748347632L,
                timezone = 3600
            )
        ),
        dayForecastState = DayForecastState()
    )
)