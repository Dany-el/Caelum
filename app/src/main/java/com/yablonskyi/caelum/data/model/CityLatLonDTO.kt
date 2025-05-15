package com.yablonskyi.caelum.data.model

import com.google.gson.annotations.SerializedName
import com.yablonskyi.caelum.domain.model.City

data class CityLatLonDTO(
    val name: String,
    @SerializedName("local_names")
    val localNames: Map<String, String>?,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)

fun CityLatLonDTO.toCityLatLon(): City = City(
    name, lat, lon
)