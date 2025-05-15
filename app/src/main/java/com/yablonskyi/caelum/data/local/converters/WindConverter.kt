package com.yablonskyi.caelum.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.yablonskyi.caelum.domain.model.Wind

class WindConverter {
    @TypeConverter
    fun fromWind(wind: Wind): String = Gson().toJson(wind)

    @TypeConverter
    fun toWind(json: String): Wind = Gson().fromJson(json, Wind::class.java)
}
