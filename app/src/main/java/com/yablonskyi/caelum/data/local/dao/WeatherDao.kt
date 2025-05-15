package com.yablonskyi.caelum.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yablonskyi.caelum.data.local.entity.DayForecastEntity
import com.yablonskyi.caelum.data.local.entity.HourForecastEntity

@Dao
interface WeatherDao {

    @Query(
        """
    SELECT * FROM day_forecast 
    WHERE ABS(lat - :lat) < 0.0001 AND ABS(lon - :lon) < 0.0001 
    LIMIT 1
    """
    )
    suspend fun getDayForecastByLocation(lat: Double, lon: Double): DayForecastEntity?


    @Insert
    suspend fun save(forecast: DayForecastEntity)

    @Update
    suspend fun update(forecast: DayForecastEntity)

    @Query(
        """
    SELECT * FROM hour_forecast 
    WHERE ABS(lat - :lat) < 0.0001 AND ABS(lon - :lon) < 0.0001 
    LIMIT 1
    """
    )
    suspend fun getHourForecastByLocation(lat: Double, lon: Double): HourForecastEntity?

    @Insert
    suspend fun save(hourForecast: HourForecastEntity)

    @Update
    suspend fun update(hourForecast: HourForecastEntity)
}
