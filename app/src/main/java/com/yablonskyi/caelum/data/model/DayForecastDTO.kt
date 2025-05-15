package com.yablonskyi.caelum.data.model

import com.google.gson.annotations.SerializedName
import com.yablonskyi.caelum.domain.model.Clouds
import com.yablonskyi.caelum.domain.model.DayForecast
import com.yablonskyi.caelum.domain.model.ForecastItem
import com.yablonskyi.caelum.domain.model.Main
import com.yablonskyi.caelum.domain.model.Weather
import com.yablonskyi.caelum.domain.model.Wind

data class DayForecastDTO(
    val list: List<ForecastItemDTO>,
    val city: CityDTO
)

data class ForecastItemDTO(
    val dt: Long,
    val main: MainDTO,
    val weather: List<WeatherDTO>,
    val clouds: CloudsDTO,
    val wind: WindDTO,
    val visibility: Int,
    val pop: Double,
    val rain: RainDTO? = null,
    @SerializedName("dt_txt") val dtString: String
)

data class MainDTO(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Int,
    @SerializedName("sea_level") val seaLevel: Int,
    @SerializedName("grnd_level") val groundLevel: Int,
    val humidity: Int,
    @SerializedName("temp_kf") val tempKf: Double
)

data class WeatherDTO(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class CloudsDTO(val all: Int)

data class WindDTO(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

data class RainDTO(
    @SerializedName("3h") val volume: Double
)

data class CityDTO(
    val id: Int,
    val name: String,
    val coord: CoordDTO,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)

data class CoordDTO(
    val lat: Double,
    val lon: Double
)


fun DayForecastDTO.toDayForecast(): DayForecast {
    return DayForecast(
        list = this.list.map { item ->
            ForecastItem(
                dt = item.dt,
                main = Main(
                    temp = item.main.temp,
                    feelsLike = item.main.feelsLike,
                    pressure = item.main.pressure,
                    humidity = item.main.humidity
                ),
                weather = item.weather.map { Weather(main = it.main, description = it.description) },
                clouds = Clouds(all = item.clouds.all),
                wind = Wind(
                    speed = item.wind.speed,
                    deg = item.wind.deg
                ),
                visibility = item.visibility,
                pop = item.pop,
                dtString = item.dtString
            )
        }
    )
}
