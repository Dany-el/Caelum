package com.yablonskyi.caelum.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class City(
    val name: String,
    val lat: Double,
    val lon: Double,
    val id: Long = 0
)
