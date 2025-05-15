package com.yablonskyi.caelum.data.di

import android.content.Context
import com.yablonskyi.caelum.data.local.WeatherPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @Singleton
    fun provideWeatherPreferences(@ApplicationContext context: Context): WeatherPreferences {
        return WeatherPreferences(context)
    }
}