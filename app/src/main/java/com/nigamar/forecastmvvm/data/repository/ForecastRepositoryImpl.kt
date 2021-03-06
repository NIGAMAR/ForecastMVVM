package com.nigamar.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.nigamar.forecastmvvm.data.db.CurrentWeatherDao
import com.nigamar.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.nigamar.forecastmvvm.data.network.WeatherNetworkDataSource
import com.nigamar.forecastmvvm.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
): ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{newCurrentWeather->
            // persist the weather when ever it is updated
            persistFetchedWeatherData(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }
    private fun persistFetchedWeatherData(fetchedWeatherData:CurrentWeatherResponse){
        GlobalScope.launch (Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeatherData.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(){
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "India",
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime:ZonedDateTime):Boolean{
        val thirtyMinutesAgo= ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}