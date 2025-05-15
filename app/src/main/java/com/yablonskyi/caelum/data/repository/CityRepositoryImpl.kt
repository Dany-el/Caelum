package com.yablonskyi.caelum.data.repository

import com.yablonskyi.caelum.data.local.dao.CityDao
import com.yablonskyi.caelum.data.local.entity.CityEntity
import com.yablonskyi.caelum.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
): CityRepository {
    override suspend fun saveCity(city: CityEntity) {
        cityDao.insertCity(city)
    }

    override suspend fun deleteCity(city: CityEntity) {
        cityDao.deleteCity(city)
    }

    override suspend fun getSavedCities(): List<CityEntity> {
        return cityDao.getAllCities()
    }
}