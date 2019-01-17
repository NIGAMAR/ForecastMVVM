package com.nigamar.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.nigamar.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric:Boolean):LiveData<out UnitSpecificCurrentWeatherEntry>
}