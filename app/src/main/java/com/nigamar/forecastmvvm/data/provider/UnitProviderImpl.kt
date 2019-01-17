package com.nigamar.forecastmvvm.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.nigamar.forecastmvvm.internal.UnitSystem

const val UNIT_SYSTEM="UNIT_SYSTEM"
class UnitProviderImpl(context: Context): UnitProvider {
    private val appContext=context.applicationContext
    private val sharedPreferences:SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)
    override fun getUnitSystem(): UnitSystem {
        val selectedName = sharedPreferences.getString(UNIT_SYSTEM,UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName)
    }
}