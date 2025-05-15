package com.yablonskyi.caelum.data.local.mapper

import com.yablonskyi.caelum.data.local.entity.DayForecastEntity
import com.yablonskyi.caelum.domain.model.DayForecast

fun DayForecastEntity.toDomain(): DayForecast = DayForecast(
    list = list
)

fun DayForecast.toEntity(lat: Double, lon: Double): DayForecastEntity = DayForecastEntity(
    lat = lat,
    lon = lon,
    list = list,
    timestamp = System.currentTimeMillis()
)