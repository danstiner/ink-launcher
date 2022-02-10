package com.danielstiner.ink.launcher.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "launches")
data class Launch(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "package") val packageName: String,
    @ColumnInfo(name = "at") val at: Instant,
)
