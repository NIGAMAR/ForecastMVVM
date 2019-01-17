package com.nigamar.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.nigamar.forecastmvvm.data.repository.ForecastRepository
import com.nigamar.forecastmvvm.internal.UnitSystem
import com.nigamar.forecastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
): ViewModel() {
    private val unitSystem=UnitSystem.METRIC
    val isMetric:Boolean
     get() = unitSystem==UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
