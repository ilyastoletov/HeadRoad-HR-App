package com.appninjas.data.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MetricsMapConverter {

    @JvmStatic
    @TypeConverter
    fun mapToString(map: Map<String, Int>): String {
        val gsonConverter = Gson()
        return gsonConverter.toJson(map)
    }

    @JvmStatic
    @TypeConverter
    fun stringToMap(metricsJson: String): Map<String, Int> {
        val mapType = object : TypeToken<Map<String, Int>>() {}.type
        return Gson().fromJson(metricsJson, mapType)
    }
}