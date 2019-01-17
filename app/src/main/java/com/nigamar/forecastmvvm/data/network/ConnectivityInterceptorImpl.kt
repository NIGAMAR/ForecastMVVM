package com.nigamar.forecastmvvm.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.nigamar.forecastmvvm.internal.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {
    private val appContext=context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline())
            throw NoNetworkException()
        return chain.proceed(chain.request())
    }

    fun isOnline():Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo= connectivityManager.activeNetworkInfo
        return networkInfo!=null && networkInfo.isConnected
    }
}