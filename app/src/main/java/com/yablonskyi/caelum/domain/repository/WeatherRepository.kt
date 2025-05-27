package com.yablonskyi.caelum.domain.repository

import com.yablonskyi.caelum.data.model.CityLatLonDTO
import com.yablonskyi.caelum.domain.model.DayForecast
import com.yablonskyi.caelum.domain.model.HourForecast

interface WeatherRepository {
    suspend fun getCoordinatesByCity(
        city: String,
        limit: Int = 1,
    ): List<CityLatLonDTO>

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String = "metric",
        lang: String = "en",
    ): HourForecast

    suspend fun getDayForecast(
        lat: Double,
        lon: Double,
        units: String = "metric",
        cnt: Int = 8,
        lang: String = "en",
    ): DayForecast
}