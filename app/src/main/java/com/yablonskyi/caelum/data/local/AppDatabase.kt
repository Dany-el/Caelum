package com.yablonskyi.caelum.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yablonskyi.caelum.data.local.dao.CityDao
import com.yablonskyi.caelum.data.local.dao.WeatherDao
import com.yablonskyi.caelum.data.local.entity.CityEntity
import com.yablonskyi.caelum.data.local.entity.DayForecastEntity
import com.yablonskyi.caelum.data.local.entity.HourForecastEntity

@Database(
    entities = [
        CityEntity::class,
        HourForecastEntity::class,
        DayForecastEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun cityDao(): CityDao
}
