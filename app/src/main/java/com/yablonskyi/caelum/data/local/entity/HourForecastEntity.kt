package com.yablonskyi.caelum.data.local.entity

import androidx.room.BuiltInTypeConverters
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yablonskyi.caelum.data.local.converters.CloudsConverter
import com.yablonskyi.caelum.data.local.converters.MainConverter
import com.yablonskyi.caelum.data.local.converters.WeatherListConverter
import com.yablonskyi.caelum.data.local.converters.WindConverter
import com.yablonskyi.caelum.domain.model.Clouds
import com.yablonskyi.caelum.domain.model.Main
import com.yablonskyi.caelum.domain.model.Weather
import com.yablonskyi.caelum.domain.model.Wind

@Entity(
    tableName = "hour_forecast",
)
@TypeConverters(
    WeatherListConverter::class,
    MainConverter::class,
    WindConverter::class,
    CloudsConverter::class,
    builtInTypeConverters = BuiltInTypeConverters()
)
data class HourForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lat: Double,
    val lon: Double,
    val weather: List<Weather>,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val sunrise: Long,
    val sunset: Long,
    val time: Long,
    val timezone: Long,
    val timestamp: Long
)