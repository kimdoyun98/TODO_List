package com.project.data.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.data.local.room.entity.RoutineEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converter {
    @TypeConverter
    fun listToJson(value: List<Boolean>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Boolean>? {
        return Gson().fromJson(value, Array<Boolean>::class.java)?.toList()
    }

    @TypeConverter
    fun localDateTimeToString(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun stringToLocalDateTime(dateString: String?): LocalDate? {
        return dateString?.toLocalDate()
    }

    @TypeConverter
    fun mapToJson(value: Map<Int, RoutineEntity>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToMap(value: String): Map<Int, RoutineEntity>? {
        return Gson().fromJson(value, object : TypeToken<Map<Int, RoutineEntity>>() {}.type)
    }

    private fun String.toLocalDate(): LocalDate = LocalDate
        .parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
}
