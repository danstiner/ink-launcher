package com.danielstiner.ink.launcher.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danielstiner.ink.launcher.data.AppRepository
import com.danielstiner.ink.launcher.data.LocationRepository
import com.danielstiner.ink.launcher.data.WeatherRepository
import com.danielstiner.ink.launcher.data.db.Database
import com.danielstiner.ink.launcher.data.source.CategoryDataSource
import com.danielstiner.ink.launcher.data.source.GeoIp
import com.danielstiner.ink.launcher.data.source.LaunchHistoryDataSource
import com.danielstiner.ink.launcher.data.source.PackageDataSource
import kotlin.time.ExperimentalTime

@ExperimentalTime
class SharedViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            val database = Database.getInstance(context)
            val categories = CategoryDataSource()
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(
                database = database,
                appRepository = AppRepository(
                    packageManager = context.packageManager,
                    categories = categories,
                    launchHistory = LaunchHistoryDataSource(
                        launches = database.launchDao(),
                        categories = categories,
                        packageDataSource = PackageDataSource(packageManager = context.packageManager)
                    )
                ),
                locationRepository = LocationRepository(context, GeoIp()),
                weatherRepository = WeatherRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}