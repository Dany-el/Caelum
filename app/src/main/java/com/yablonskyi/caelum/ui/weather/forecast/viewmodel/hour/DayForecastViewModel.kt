package com.yablonskyi.caelum.ui.weather.forecast.viewmodel.hour

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yablonskyi.caelum.domain.usecase.weather.GetDayForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DayForecastViewModel @Inject constructor(
    private val getDayForecastUseCase: GetDayForecastUseCase
) : ViewModel() {
    private val _state = mutableStateOf(DayForecastState())
    val state: State<DayForecastState> get() = _state


}
