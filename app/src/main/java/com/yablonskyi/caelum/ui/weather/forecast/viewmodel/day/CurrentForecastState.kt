package com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day

import com.yablonskyi.caelum.domain.model.HourForecast

data class CurrentForecastState(
    val isLoading: Boolean = false,
    val forecast: HourForecast? = null,
    val errorMsg: String = ""
)
