package com.yablonskyi.caelum.domain.usecase.weather

import com.yablonskyi.caelum.common.Resource
import com.yablonskyi.caelum.data.model.toCityLatLon
import com.yablonskyi.caelum.domain.model.City
import com.yablonskyi.caelum.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoordinatesByCityUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(
        city: String,
        limit: Int = 1,
    ): Flow<Resource<City>> = flow {
        try {
            emit(Resource.Loading())
            val forecast = repository.getCoordinatesByCity(city, limit)[0].toCityLatLon()
            emit(Resource.Success(forecast))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: IndexOutOfBoundsException) {
            emit(Resource.Error("City was not found"))
        }
    }
}