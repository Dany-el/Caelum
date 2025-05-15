package com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day

import com.yablonskyi.caelum.domain.model.City

data class CityCoordState(
    val isLoading: Boolean = false,
    val city: City? = null,
    val errorMsg: String = ""
)