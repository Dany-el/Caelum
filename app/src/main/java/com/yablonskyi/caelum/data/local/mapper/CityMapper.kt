package com.yablonskyi.caelum.data.local.mapper

import com.yablonskyi.caelum.data.local.entity.CityEntity
import com.yablonskyi.caelum.domain.model.City


fun City.toEntity(): CityEntity {
    return CityEntity(
        id = this.id,
        name = this.name,
        lat = this.lat,
        lon = this.lon
    )
}

fun CityEntity.toDomain(): City {
    return City(
        id = this.id,
        name = this.name,
        lat = this.lat,
        lon = this.lon
    )
}