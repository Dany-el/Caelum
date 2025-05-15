package com.yablonskyi.caelum.data.di

import android.content.Context
import androidx.room.Room
import com.yablonskyi.caelum.data.local.AppDatabase
import com.yablonskyi.caelum.data.local.dao.CityDao
import com.yablonskyi.caelum.data.local.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "caelum_database"
        ).build()
    }

    @Provides
    fun provideWeatherDao(db: AppDatabase): WeatherDao = db.weatherDao()

    @Provides
    fun provideCityDao(db: AppDatabase): CityDao = db.cityDao()
}
