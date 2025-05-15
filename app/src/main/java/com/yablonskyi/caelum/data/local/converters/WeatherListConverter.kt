package com.yablonskyi.caelum.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yablonskyi.caelum.domain.model.Weather

class WeatherListConverter {
    @TypeConverter
    fun fromWeatherList(list: List<Weather>): String = Gson().toJson(list)

    @TypeConverter
    fun toWeatherList(json: String): List<Weather> =
        Gson().fromJson(json, object : TypeToken<List<Weather>>() {}.type)
}
