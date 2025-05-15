package com.yablonskyi.caelum.data.di

import android.util.Log
import com.yablonskyi.caelum.common.Constants
import com.yablonskyi.caelum.data.remote.WeatherApi
import com.yablonskyi.caelum.data.repository.WeatherRepositoryImpl
import com.yablonskyi.caelum.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.yablonskyi.caelum.BuildConfig
import com.yablonskyi.caelum.data.local.dao.WeatherDao
import com.yablonskyi.caelum.data.local.WeatherPreferences
import com.yablonskyi.caelum.data.local.dao.CityDao
import com.yablonskyi.caelum.data.repository.CityRepositoryImpl
import com.yablonskyi.caelum.domain.repository.CityRepository
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        val apiKey = BuildConfig.WEATHER_API_KEY
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .addInterceptor(logging)
            .build()


        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi,
        dao: WeatherDao,
    ): WeatherRepository {
        return WeatherRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideCityRepository(
        cityDao: CityDao
    ): CityRepository {
        return CityRepositoryImpl(cityDao)
    }
}
