package com.yablonskyi.caelum.data.model

import com.yablonskyi.caelum.domain.model.Clouds
import com.yablonskyi.caelum.domain.model.HourForecast
import com.yablonskyi.caelum.domain.model.Main
import com.yablonskyi.caelum.domain.model.Weather
import com.yablonskyi.caelum.domain.model.Wind

data class HourForecastDTO(
    val coord: CoordDTO,
    val weather: List<WeatherDTO>,
    val base: String,
    val main: MainDTO,
    val visibility: Int,
    val wind: WindDTO,
    val clouds: CloudsDTO,
    val dt: Long,
    val sys: SysDTO,
    val timezone: Long,
    val id: Int,
    val name: String,
    val cod: Int
)

data class SysDTO(
    val sunrise: Long,
    val sunset: Long
)

fun HourForecastDTO.toHourForecast(): HourForecast {
    return HourForecast(
        weather = this.weather.map { Weather(main = it.main, description = it.description) },
        main = Main(
            temp = this.main.temp,
            feelsLike = this.main.feelsLike,
            pressure = this.main.pressure,
            humidity = this.main.humidity
        ),
        visibility = this.visibility,
        wind = Wind(
            speed = this.wind.speed,
            deg = this.wind.deg
        ),
        clouds = Clouds(
            all = this.clouds.all
        ),
        sunrise = this.sys.sunrise,
        sunset = this.sys.sunset,
        time = this.dt,
        timezone = this.timezone
    )
}