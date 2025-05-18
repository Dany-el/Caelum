package com.yablonskyi.caelum.data.repository

import android.util.Log
import com.yablonskyi.caelum.data.local.dao.WeatherDao
import com.yablonskyi.caelum.data.local.mapper.toDomain
import com.yablonskyi.caelum.data.local.mapper.toEntity
import com.yablonskyi.caelum.data.model.CityLatLonDTO
import com.yablonskyi.caelum.data.model.toDayForecast
import com.yablonskyi.caelum.data.model.toHourForecast
import com.yablonskyi.caelum.data.remote.WeatherApi
import com.yablonskyi.caelum.domain.model.DayForecast
import com.yablonskyi.caelum.domain.model.HourForecast
import com.yablonskyi.caelum.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao,
) :
    WeatherRepository {
    override suspend fun getCoordinatesByCity(city: String, limit: Int): List<CityLatLonDTO> {
        return withContext(Dispatchers.IO) { api.getCoordinatesByCity(city, limit) }
    }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
    ): HourForecast {
        return withContext(Dispatchers.IO) {
            val cached = dao.getHourForecastByLocation(lat, lon)
            val now = System.currentTimeMillis()
            val oneHourAgo = now - TimeUnit.HOURS.toMillis(1)

            if (cached == null || cached.timestamp < oneHourAgo) {
                val response = api.getCurrentWeather(lat, lon, units, lang)
                val entity = response.toHourForecast().toEntity(lat, lon)
                if (cached == null) {
                    Log.i("RoomDB", "Saved response: $entity")
                    dao.save(entity)
                } else {
                    Log.i("RoomDB", "Updated response: $entity")
                    dao.update(entity)
                }
                entity.toDomain()
            } else {
                Log.i("RoomDB", "Loaded cached response: $cached")
                cached.toDomain()
            }
        }
    }

    override suspend fun getDayForecast(
        lat: Double,
        lon: Double,
        units: String,
        cnt: Int,
        lang: String
    ): DayForecast {
        return withContext(Dispatchers.IO) {
            val cached = dao.getDayForecastByLocation(lat, lon)
            val now = System.currentTimeMillis()
            val oneHourAgo = now - TimeUnit.HOURS.toMillis(1)

            if (cached == null || cached.timestamp < oneHourAgo) {
                val response = api.getDayForecast(lat, lon, units, cnt, lang)
                val entity = response.toDayForecast().toEntity(lat, lon)
                if (cached == null) {
                    Log.i("RoomDB", "Saved response: $entity")
                    dao.save(entity)
                } else {
                    Log.i("RoomDB", "Updated response: $entity")
                    dao.update(entity)
                }
                entity.toDomain()
            } else {
                Log.i("RoomDB", "Loaded cached response: $cached")
                cached.toDomain()
            }
        }
    }
}