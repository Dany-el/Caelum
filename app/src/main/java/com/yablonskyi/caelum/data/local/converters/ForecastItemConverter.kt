package com.yablonskyi.caelum.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yablonskyi.caelum.domain.model.ForecastItem

class ForecastItemListConverter {
    @TypeConverter
    fun fromList(list: List<ForecastItem>): String = Gson().toJson(list)

    @TypeConverter
    fun toList(json: String): List<ForecastItem> =
        Gson().fromJson(json, object : TypeToken<List<ForecastItem>>() {}.type)
}