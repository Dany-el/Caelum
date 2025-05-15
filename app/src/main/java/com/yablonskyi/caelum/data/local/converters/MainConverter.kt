package com.yablonskyi.caelum.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.yablonskyi.caelum.domain.model.Main

class MainConverter {
    @TypeConverter
    fun fromMain(main: Main): String = Gson().toJson(main)

    @TypeConverter
    fun toMain(json: String): Main = Gson().fromJson(json, Main::class.java)
}
