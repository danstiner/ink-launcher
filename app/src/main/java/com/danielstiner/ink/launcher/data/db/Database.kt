package com.danielstiner.ink.launcher.data.db

import android.content.Context
import androidx.annotation.AnyThread
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicReference

@androidx.room.Database(
    entities = [Launch::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = Database.Migration1To2::class)
    ],
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun launchDao(): LaunchDao

    companion object {

        private val INSTANCE_REF: AtomicReference<WeakReference<Database>> =
            AtomicReference(WeakReference(null))

        @AnyThread
        fun getInstance(context: Context): Database {
            var database: Database? = INSTANCE_REF.get().get()
            if (database == null) {
                database = Room.databaseBuilder(
                    context,
                    Database::class.java, "database"
                ).build()
                INSTANCE_REF.set(WeakReference(database))
            }
            return database
        }

    }

    @RenameTable(fromTableName = "launch_dates", toTableName = "launches")
    class Migration1To2 : AutoMigrationSpec

}