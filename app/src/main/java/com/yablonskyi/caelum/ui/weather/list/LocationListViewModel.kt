package com.yablonskyi.caelum.ui.weather.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yablonskyi.caelum.domain.model.City
import com.yablonskyi.caelum.domain.usecase.city.CityUseCases
import com.yablonskyi.caelum.ui.weather.forecast.viewmodel.day.LocationListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationListViewModel @Inject constructor(
    private val cityUseCases: CityUseCases
) : ViewModel() {

    private val _state = mutableStateOf(LocationListState())
    val state: State<LocationListState> = _state

    init {
        loadSavedCities()
    }

    fun loadSavedCities() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val cities = cityUseCases.getSavedCities()
                _state.value = _state.value.copy(
                    isLoading = false,
                    cities = cities,
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMsg = e.message ?: "Unknown error"
                )
            }
        }
    }

    fun saveCity(city: City) {
        viewModelScope.launch {
            try {
                cityUseCases.saveCity(city)
                loadSavedCities()
            } catch (e: Exception) {
                _state.value = _state.value.copy(errorMsg = e.message ?: "Save failed")
            }
        }
    }

    fun deleteCity(city: City) {
        viewModelScope.launch {
            try {
                cityUseCases.deleteCity(city)
                loadSavedCities()
            } catch (e: Exception) {
                _state.value = _state.value.copy(errorMsg = e.message ?: "Delete failed")
            }
        }
    }
}

