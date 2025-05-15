package com.yablonskyi.caelum.data.remote

import com.yablonskyi.caelum.data.model.CityLatLonDTO
import com.yablonskyi.caelum.data.model.DayForecastDTO
import com.yablonskyi.caelum.data.model.HourForecastDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("geo/1.0/direct")
    suspend fun getCoordinatesByCity(
        @Query("q") city: String,
        @Query("limit") limit: Int = 1,
    ): List<CityLatLonDTO>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ua",
    ): HourForecastDTO

    @GET("data/2.5/forecast")
    suspend fun getDayForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("cnt") cnt: Int = 8,
        @Query("lang") lang: String = "ua",
    ): DayForecastDTO
}