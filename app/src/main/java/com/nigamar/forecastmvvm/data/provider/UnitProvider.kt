package com.nigamar.forecastmvvm.data.provider

import com.nigamar.forecastmvvm.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem():UnitSystem
}