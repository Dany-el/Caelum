package com.yablonskyi.caelum.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.yablonskyi.caelum.domain.model.Clouds

class CloudsConverter {
    @TypeConverter
    fun fromClouds(clouds: Clouds): String = Gson().toJson(clouds)

    @TypeConverter
    fun toClouds(json: String): Clouds = Gson().fromJson(json, Clouds::class.java)
}
