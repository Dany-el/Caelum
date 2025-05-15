package com.yablonskyi.caelum.data.local.mapper

import com.yablonskyi.caelum.data.local.entity.HourForecastEntity
import com.yablonskyi.caelum.domain.model.HourForecast

fun HourForecastEntity.toDomain(): HourForecast = HourForecast(
    weather = weather,
    main = main,
    visibility = visibility,
    wind = wind,
    clouds = clouds,
    sunrise = sunrise,
    sunset = sunset,
    timezone = timezone
)

fun HourForecast.toEntity(lat: Double, lon: Double): HourForecastEntity = HourForecastEntity(
    lat = lat,
    lon = lon,
    weather = weather,
    main = main,
    visibility = visibility,
    wind = wind,
    clouds = clouds,
    sunrise = sunrise,
    sunset = sunset,
    timezone = timezone,
    timestamp = System.currentTimeMillis()
)
