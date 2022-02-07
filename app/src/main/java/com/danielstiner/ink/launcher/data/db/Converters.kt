package com.danielstiner.ink.launcher.data.db

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) }
    }

    @TypeConverter
    fun toTimestamp(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }

    @TypeConverter
    fun fromLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, ISO_LOCAL_DATE) }
    }

    @TypeConverter
    fun toLocalDate(instant: LocalDate?): String? {
        return instant?.format(ISO_LOCAL_DATE)
    }
}
