package com.nigamar.forecastmvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nigamar.forecastmvvm.data.db.entity.CurrentWeatherEntry


@Database(entities = arrayOf(CurrentWeatherEntry::class) ,version = 1)
abstract class ForecastDatabase:RoomDatabase() {
    abstract fun currentWeatherDao():CurrentWeatherDao
    companion object {
       @Volatile private var INSTANCE: ForecastDatabase?=null
        private val LOCK=Any()
        fun createDatabase(context:Context):ForecastDatabase{
            synchronized(LOCK){
                INSTANCE ?: buildDatabase(context).also {forecastDatabase ->
                    INSTANCE=forecastDatabase
                }
            }
            return INSTANCE!!
        }

        fun buildDatabase(context: Context)=
                Room.databaseBuilder(
                    context.applicationContext,
                    ForecastDatabase::class.java,
                    "forecast.db").build()
    }
}