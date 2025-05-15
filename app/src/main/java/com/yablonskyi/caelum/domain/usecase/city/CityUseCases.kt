package com.yablonskyi.caelum.domain.usecase.city

import com.yablonskyi.caelum.data.local.mapper.toDomain
import com.yablonskyi.caelum.data.local.mapper.toEntity
import com.yablonskyi.caelum.domain.model.City
import com.yablonskyi.caelum.domain.repository.CityRepository
import javax.inject.Inject

data class CityUseCases @Inject constructor(
    val saveCity: SaveCity,
    val deleteCity: DeleteCity,
    val getSavedCities: GetSavedCities
) {
    class SaveCity @Inject constructor(
        private val repository: CityRepository
    ) {
        suspend operator fun invoke(city: City) {
            repository.saveCity(city.toEntity())
        }
    }

    class DeleteCity @Inject constructor(
        private val repository: CityRepository
    ) {
        suspend operator fun invoke(city: City) {
            repository.deleteCity(city.toEntity())
        }
    }

    class GetSavedCities @Inject constructor(
        private val repository: CityRepository
    ) {
        suspend operator fun invoke(): List<City> {
            return repository.getSavedCities().map { it.toDomain() }
        }
    }
}