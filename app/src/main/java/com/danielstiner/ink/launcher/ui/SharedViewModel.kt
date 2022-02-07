package com.danielstiner.ink.launcher.ui

import android.content.Context
import android.content.Intent
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danielstiner.ink.launcher.data.AppRepository
import com.danielstiner.ink.launcher.data.LocationRepository
import com.danielstiner.ink.launcher.data.WeatherRepository
import com.danielstiner.ink.launcher.data.db.Database
import com.danielstiner.ink.launcher.data.db.Launch
import com.danielstiner.ink.launcher.data.model.AppItem
import com.danielstiner.ink.launcher.data.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit.MILLIS
import java.util.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
class SharedViewModel(
    private val database: Database,
    private val appRepository: AppRepository,
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) :
    androidx.lifecycle.ViewModel() {

    private var cleared: Boolean = false

    private val _apps = MutableLiveData<List<AppItem>>()
    val apps: LiveData<List<AppItem>> = _apps

    private val _localDate = MutableLiveData<Date>()
    val localDate: LiveData<Date> = _localDate

    private val _weather = MutableLiveData<Weather?>()
    val weather: LiveData<Weather?> = _weather

    init {
        launchBackgroundTasks()
    }

    fun onLocationAccessGranted() {
        viewModelScope.launch {
            fetchWeather()
        }
    }

    private fun launchBackgroundTasks() {

        // Keep local date updated
        // TODO take into account timezone changes
        viewModelScope.launch {
            while (!cleared) {
                val zone = ZoneId.systemDefault()
                val now = ZonedDateTime.now(zone)
                val startOfNextDay = now.plusDays(1).toLocalDate().atStartOfDay(zone)

                _localDate.postValue(Date.from(now.toInstant()))

                delay(now.until(startOfNextDay, MILLIS))
            }
        }

        // Periodically refresh app list
        // TODO Instead listen for app add/remove broadcasts and refresh the list of apps
        // https://developer.android.com/reference/android/content/Intent#ACTION_PACKAGE_ADDED
        viewModelScope.launch {
            while (!cleared) {
                fetchAppList()
                delay(APPLIST_REFRESH_PERIOD.toMillis())
            }
        }

        // Periodically fetch weather
        viewModelScope.launch {
            while (!cleared) {
                fetchWeather()
                delay(WEATHER_REFRESH_PERIOD.toMillis())
            }
        }
    }

    @MainThread
    fun launchApp(
        appItem: AppItem,
        context: Context,
    ) {
        launchPackage(appItem.packageName.toString(), context)
    }

    @MainThread
    fun launchSelector(selectorAction: String, selectorCategory: String, context: Context) {
        launchIntent(
            Intent.makeMainSelectorActivity(
                selectorAction,
                selectorCategory
            ),
            context
        )
    }

    @MainThread
    fun launchPackage(
        packageName: String,
        context: Context,
    ) {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            launchIntent(intent, context)
        } else {
            TODO("Unhandled no launch intent for package error")
        }
    }

    @MainThread
    private fun launchIntent(intent: Intent, context: Context) {
        context.startActivity(intent)
        viewModelScope.launch(Dispatchers.IO) {
            database.launchDao().insert(
                Launch(
                    packageName = intent.`package`,
                    category = intent.selector?.categories?.first(),
                    action = intent.action,
                    at = Instant.now()
                )
            )
        }
    }

    private suspend fun fetchAppList() {
        _apps.postValue(appRepository.get())
    }

    private suspend fun fetchWeather() {
        val location = locationRepository.lastLocation()

        if (location == null) {
            val w = weather.value
            if (w != null && Instant.now().isAfter(w.time + WEATHER_TIMEOUT_PERIOD)) {
                _weather.value = null
            }
            return
        }

        val weather = weatherRepository.fetch(location)
        _weather.postValue(weather)
    }

    override fun onCleared() {
        super.onCleared()
        cleared = true
    }

    companion object {
        private const val TAG = "SharedViewModel"
        private val APPLIST_REFRESH_PERIOD = Duration.ofHours(1)
        private val WEATHER_REFRESH_PERIOD = Duration.ofHours(1)
        private val WEATHER_TIMEOUT_PERIOD = Duration.ofHours(4)
    }
}