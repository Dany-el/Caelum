package com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yablonskyi.caelum.common.Resource
import com.yablonskyi.caelum.common.Units
import com.yablonskyi.caelum.domain.model.City
import com.yablonskyi.caelum.domain.usecase.weather.GetCoordinatesByCityUseCase
import com.yablonskyi.caelum.domain.usecase.weather.GetCurrentWeatherUseCase
import com.yablonskyi.caelum.domain.usecase.weather.GetDayForecastUseCase
import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.hour.DayForecastState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getCoordinatesByCityUseCase: GetCoordinatesByCityUseCase,
    private val getCurrentForecastUseCase: GetCurrentWeatherUseCase,
    private val getDayForecastUseCase: GetDayForecastUseCase
) : ViewModel() {

    private val _coordState = mutableStateOf(CityCoordState())
    val cityCoordState: State<CityCoordState> get() = _coordState

    private val _units = mutableStateOf(Units.METRICS)
    val units: State<Units> get() = _units

    private val _forecastList = mutableStateListOf<ForecastBundle>()
    val forecastList: SnapshotStateList<ForecastBundle> get() = _forecastList

    fun getForecastForCity(cityName: String) {
        if (isCityAlreadyInList(cityName)) return

        loadCityCoordinates(cityName)
    }

    private fun isCityAlreadyInList(cityName: String): Boolean {
        val exists = _forecastList.any {
            it.cityState.city?.name.equals(cityName, ignoreCase = true)
        }

        if (exists) {
            Log.i("Forecast", "City $cityName already exists in list, skipping API call")
        }

        return exists
    }

    private fun loadCityCoordinates(cityName: String) {
        getCoordinatesByCityUseCase(cityName).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _coordState.value =
                        CityCoordState(errorMsg = result.message ?: "An error occurred")
                    Log.i("CoordResult", "Error: ${result.message}")
                }

                is Resource.Loading -> {
                    _coordState.value = CityCoordState(isLoading = true)
                    Log.i("CoordResult", "Loading")
                }

                is Resource.Success -> {
                    val city = result.data
                    _coordState.value = CityCoordState(city = city)
                    _coordState.value.city?.let { coords ->
                        addCityToForecastList(cityName, coords)
                        loadForecastsForCity(cityName, coords)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addCityToForecastList(cityName: String, coords: City) {
        val newBundle = ForecastBundle(
            cityState = CityCoordState(city = coords),
            currentForecastState = CurrentForecastState(isLoading = true),
            dayForecastState = DayForecastState(isLoading = true)
        )
        _forecastList.add(newBundle)
    }

    private fun loadForecastsForCity(cityName: String, coords: City) {
        loadCurrentForecast(cityName, coords)
        loadDayForecast(cityName, coords)
    }

    private fun loadCurrentForecast(cityName: String, coords: City) {
        getCurrentForecastUseCase(coords.lat, coords.lon, _units.value.param)
            .onEach { result ->
                updateForecastBundle(cityName) { old ->
                    old.copy(currentForecastState = when (result) {
                        is Resource.Success -> CurrentForecastState(forecast = result.data)
                        is Resource.Error -> CurrentForecastState(errorMsg = result.message ?: "Error")
                        is Resource.Loading -> CurrentForecastState(isLoading = true)
                    })
                }
            }.launchIn(viewModelScope)
    }

    private fun loadDayForecast(cityName: String, coords: City) {
        getDayForecastUseCase(coords.lat, coords.lon, _units.value.param)
            .onEach { result ->
                updateForecastBundle(cityName) { old ->
                    old.copy(dayForecastState = when (result) {
                        is Resource.Success -> DayForecastState(forecast = result.data)
                        is Resource.Error -> DayForecastState(errorMsg = result.message ?: "Error")
                        is Resource.Loading -> DayForecastState(isLoading = true)
                    })
                }
            }.launchIn(viewModelScope)
    }

    private fun updateForecastBundle(cityName: String, update: (ForecastBundle) -> ForecastBundle) {
        val index = _forecastList.indexOfFirst {
            it.cityState.city?.name.equals(cityName, ignoreCase = true)
        }

        if (index != -1) {
            _forecastList[index] = update(_forecastList[index])
        }
    }

    fun getCityCoordinates(name: String) {
        getCoordinatesByCityUseCase(name).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _coordState.value =
                        CityCoordState(errorMsg = result.message ?: "An error occurred")
                    Log.i("CoordResult", "Error")
                }

                is Resource.Loading -> {
                    _coordState.value = CityCoordState(isLoading = true)
                    Log.i("CoordResult", "Loading")
                }

                is Resource.Success -> {
                    val city = result.data
                    _coordState.value = CityCoordState(city = city)
                    Log.i("CoordResult", "Success: $city")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadAllForecasts(cities: List<City>) {
        if (cities.isNotEmpty()){
            cities.forEach { city ->
                loadCurrentForecast(city.name, city)
            }
        }
    }

    fun clearSearchResult() {
        _coordState.value = CityCoordState()
    }

    fun changeUnits(new: Units) {
        _units.value = new
    }
}