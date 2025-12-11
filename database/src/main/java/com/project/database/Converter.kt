package com.project.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.database.entity.RoutineEntity
import com.project.model.Routine
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal class Converter {
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
    fun mapToJson(value: Map<Int, Routine>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToMap(value: String): Map<Int, Routine>? {
        return Gson().fromJson(value, object : TypeToken<Map<Int, Routine>>() {}.type)
    }

    private fun String.toLocalDate(): LocalDate = LocalDate
        .parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
}
