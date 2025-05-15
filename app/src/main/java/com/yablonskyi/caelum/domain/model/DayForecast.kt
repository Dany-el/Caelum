package com.yablonskyi.caelum.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class DayForecast(
    val list: List<ForecastItem>
)

@Immutable
data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val dtString: String
)

@Immutable
data class Clouds(
    val all: Int
)
