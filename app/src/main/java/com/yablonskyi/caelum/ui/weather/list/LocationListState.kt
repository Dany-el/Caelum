package com.yablonskyi.caelum.ui.weather.list

import com.yablonskyi.caelum.domain.model.City

data class LocationListState(
    val isLoading: Boolean = false,
    val cities: List<City> = emptyList(),
    val errorMsg: String = ""
)