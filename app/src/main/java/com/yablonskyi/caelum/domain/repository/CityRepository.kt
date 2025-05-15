package com.yablonskyi.caelum.domain.repository

import com.yablonskyi.caelum.data.local.entity.CityEntity

interface CityRepository {
    suspend fun saveCity(city: CityEntity)

    suspend fun deleteCity(city: CityEntity)

    suspend fun getSavedCities(): List<CityEntity>
}