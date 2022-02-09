package com.danielstiner.ink.launcher.data.db

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LaunchDao {

    @WorkerThread
    @Insert
    fun insert(obj: Launch): Long

    @WorkerThread
    @Update
    fun update(obj: Launch)

    @WorkerThread
    @Query("SELECT * FROM launches")
    fun getAll(): List<Launch>

}