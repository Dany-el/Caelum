package com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day

import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.hour.DayForecastState

data class ForecastBundle(
    val cityState: CityCoordState,
    val currentForecastState: CurrentForecastState,
    val dayForecastState: DayForecastState
)