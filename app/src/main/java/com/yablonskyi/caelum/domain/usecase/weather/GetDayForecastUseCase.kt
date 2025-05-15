package com.yablonskyi.caelum.domain.usecase.weather

import com.yablonskyi.caelum.common.Resource
import com.yablonskyi.caelum.domain.model.DayForecast
import com.yablonskyi.caelum.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDayForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(
        lat: Double,
        lon: Double,
        units: String = "metric",
        cnt: Int = 8,
        lang: String = "en",
    ): Flow<Resource<DayForecast>> = flow {
        try {
            emit(Resource.Loading())
            val forecast = repository.getDayForecast(lat, lon, units, cnt, lang)
            emit(Resource.Success(forecast))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}