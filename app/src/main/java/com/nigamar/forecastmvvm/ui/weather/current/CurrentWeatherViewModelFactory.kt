package com.nigamar.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nigamar.forecastmvvm.data.provider.UnitProvider
import com.nigamar.forecastmvvm.data.repository.ForecastRepository

class CurrentWeatherViewModelFactory(private val forecastRepository: ForecastRepository,private val unitProvider: UnitProvider):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository,unitProvider) as T
    }
}