package com.danielstiner.ink.launcher.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danielstiner.ink.launcher.data.AppRepository
import com.danielstiner.ink.launcher.data.CategoryRepository
import com.danielstiner.ink.launcher.data.LocationRepository
import com.danielstiner.ink.launcher.data.WeatherRepository
import kotlin.time.ExperimentalTime

@ExperimentalTime
class SharedViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(
                appRepository = AppRepository(
                    packageManager = context.packageManager,
                    categoryRepository = CategoryRepository()
                ),
                locationRepository = LocationRepository(context),
                weatherRepository = WeatherRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}