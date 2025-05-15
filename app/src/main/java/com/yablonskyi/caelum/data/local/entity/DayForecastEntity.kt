package com.yablonskyi.caelum.data.local.entity

import androidx.room.BuiltInTypeConverters
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yablonskyi.caelum.data.local.converters.*
import com.yablonskyi.caelum.domain.model.*

@Entity(tableName = "day_forecast")
@TypeConverters(
    ForecastItemListConverter::class,
    builtInTypeConverters = BuiltInTypeConverters()
)
data class DayForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lat: Double,
    val lon: Double,
    val list: List<ForecastItem>,
    val timestamp: Long
)
