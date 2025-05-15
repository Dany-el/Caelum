package com.yablonskyi.caelum.domain.model

import androidx.compose.runtime.Immutable
import kotlin.math.roundToInt

@Immutable
data class HourForecast(
    val weather: List<Weather>,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val sunrise: Long,
    val sunset: Long,
    val timezone: Long,
)

@Immutable
data class Weather(
    val main: String,
    val description: String
)

@Immutable
data class Main(
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int
)

@Immutable
data class Wind(
    val speed: Double,
    val deg: Int
)

fun Int.toCompassDirection(): String {
    val directions = listOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
    val index = (((this % 360 + 360) % 360) / 45.0).roundToInt() % 8
    return directions[index]
}
