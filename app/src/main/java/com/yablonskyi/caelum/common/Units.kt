package com.yablonskyi.caelum.common

enum class Units(val param: String) {
    METRICS("metric"),
    IMPERIAL("imperial"),
    KELVIN("standard")
}

fun Units.windSpeedUnit(): String = when (this) {
    Units.METRICS -> "m/s"
    Units.IMPERIAL -> "mph"
    Units.KELVIN -> "m/s"
}

fun Units.distanceUnit(): String = when (this) {
    Units.METRICS -> "km"
    Units.IMPERIAL -> "mi"
    Units.KELVIN -> "km"
}

fun Units.humidityUnit(): String = "%"

fun Units.temperatureUnit(): Char = Typography.degree

fun Units.pressureUnit(): String = "hPa"