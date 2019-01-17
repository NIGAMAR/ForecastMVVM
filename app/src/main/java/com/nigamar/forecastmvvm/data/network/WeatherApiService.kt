package com.nigamar.forecastmvvm.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nigamar.forecastmvvm.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY="5d3814ce51684606971205251191601"
const val BASE_URL="https://api.apixu.com/v1/"

interface WeatherApiService {
    @GET("current.json")
    fun getCurrentWeather(
        @Query("q")location:String,
        @Query("lang")language:String="en"
    ):Deferred<CurrentWeatherResponse>

    companion object {
        fun createService(connectivityInterceptor: ConnectivityInterceptor): WeatherApiService {
            val requestInterceptor= Interceptor{chain ->
                val url=chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request=chain.request().newBuilder().url(url).build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient=OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return  Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}