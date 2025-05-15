package com.yablonskyi.caelum.ui.weather.forecast.viewmodel.hour

import com.yablonskyi.caelum.domain.model.DayForecast

data class DayForecastState(
    val isLoading: Boolean = false,
    val forecast: DayForecast? = null,
    val errorMsg: String = ""
)
