package com.yablonskyi.caelum.ui.weather.forecast

import com.yablonskyi.caelum.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.toIcon(description: String, time: Long, sunrise: Long, sunset: Long, timezone: Long): Int {
    val isNight = isNight(time, sunrise, sunset, timezone)

    return when (this) {
        "Clouds" -> {
            if (description == "few clouds") {
                if (isNight) R.drawable.cloudy_moon
                else R.drawable.cloud_sun
            } else R.drawable.clouds
        }

        "Clear" -> {
            if (isNight) R.drawable.moon
            else R.drawable.sun
        }

        "Snow" -> R.drawable.cloud_snowfall
        "Rain" -> R.drawable.cloud_rain
        "Drizzle" -> R.drawable.cloud_rain
        "Thunderstorm" -> R.drawable.cloud_storm
        "Mist" -> R.drawable.fog
        else -> R.drawable.sun
    }
}

fun Long.toHourMinuteTime(timeZoneOffsetSeconds: Long = 0L): String {
    val millis = (this + timeZoneOffsetSeconds) * 1000
    val date = Date(millis)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(date)
}

private fun isNight(time: Long, sunrise: Long, sunset: Long, timezone: Long): Boolean {
    val offset = ZoneOffset.ofTotalSeconds(timezone.toInt())

    val nowTime = Instant.ofEpochSecond(time).atOffset(offset).toLocalTime()
    val sunriseTime = Instant.ofEpochSecond(sunrise).atOffset(offset).toLocalTime()
    val sunsetTime = Instant.ofEpochSecond(sunset).atOffset(offset).toLocalTime()

    return nowTime.isBefore(sunriseTime) || nowTime.isAfter(sunsetTime)
}