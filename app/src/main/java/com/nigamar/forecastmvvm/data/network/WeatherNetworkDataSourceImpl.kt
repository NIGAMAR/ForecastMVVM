package com.nigamar.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nigamar.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.nigamar.forecastmvvm.internal.NoNetworkException

class WeatherNetworkDataSourceImpl(private val weatherApiService: WeatherApiService): WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather = weatherApiService.getCurrentWeather(location,languageCode).await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }catch (e: NoNetworkException){
            Log.e("Connectivity","No Internet Connection..."+e.message)
        }
    }
}