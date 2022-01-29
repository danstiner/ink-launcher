package com.danielstiner.ink.launcher.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danielstiner.ink.launcher.data.Categorizer
import com.danielstiner.ink.launcher.data.model.AppItem
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.roundToInt
import kotlin.system.measureTimeMillis

@Serializable
data class Data(val data: Timelines)

@Serializable
data class Timelines(val timelines: List<Timeline>)

@Serializable
data class Timeline(
    val timestep: String,
    val startTime: String,
    val endTime: String,
    val intervals: List<Interval>
)

@Serializable
data class Interval(val startTime: String, val endTime: String? = null, val values: Value)

@Serializable
data class Value(val temperature: Double, val temperatureApparent: Double, val weatherCode: Int)

class SharedViewModel(private val context: Context) : androidx.lifecycle.ViewModel() {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val packageManager = context.applicationContext.packageManager
    private val client = OkHttpClient()
    private val categorizer = Categorizer()

    private val _apps = MutableLiveData<List<AppItem>>()
    val apps: LiveData<List<AppItem>> = _apps

    private val _weather = MutableLiveData<String>()
    val weather: LiveData<String> = _weather

    init {
        fetchAppList()
        fetchWeather()
    }

    private fun fetchAppList() {
        viewModelScope.launch(Dispatchers.IO) {
            val time = measureTimeMillis {
                val intent =
                    Intent(Intent.ACTION_MAIN, null).apply { addCategory(Intent.CATEGORY_LAUNCHER) }

                _apps.postValue(packageManager.queryIntentActivities(intent, 0).map { info ->
                    AppItem(
                        label = info.loadLabel(packageManager),
                        packageName = info.activityInfo.packageName,
                        category = categorizer.categorize(info),
                    )
                }.sortedBy { app -> app.label.toString().lowercase() })
            }

            Log.d(TAG, "fetchAppList; time:$time")
        }
    }

    fun fetchWeather() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        viewModelScope.launch(Dispatchers.IO) { fetchWeather(location) }
                    }
                }
        } else {
            Log.d(TAG, "No location access to check weather")
        }
    }

    private val weatherCodeToText = mapOf(
        0 to "Unknown",
        1000 to "Clear",
        1001 to "Cloudy",
        1100 to "Mostly Clear",
        1101 to "Partly Cloudy",
        1102 to "Mostly Cloudy",
        2000 to "Fog",
        2100 to "Light Fog",
        3000 to "Light Wind",
        3001 to "Wind",
        3002 to "Strong Wind",
        4000 to "Drizzle",
        4001 to "Rain",
        4200 to "Light Rain",
        4201 to "Heavy Rain",
        5000 to "Snow",
        5001 to "Flurries",
        5100 to "Light Snow",
        5101 to "Heavy Snow",
        6000 to "Freezing Drizzle",
        6001 to "Freezing Rain",
        6200 to "Light Freezing Rain",
        6201 to "Heavy Freezing Rain",
        7000 to "Ice Pellets",
        7101 to "Heavy Ice Pellets",
        7102 to "Light Ice Pellets",
        8000 to "Thunderstorm"
    )

    private val weatherCodeToEmoji = mapOf(
        0 to "", // Unknown
        1000 to "", // Clear
        1001 to "☁", // Cloudy - Cloud Emoji
        1100 to "\uD83C\uDF24️", // Mostly Clear - Sun Behind Small Cloud Emoji
        1101 to "⛅", // Partly Cloudy - Sun Behind Cloud Emoji
        1102 to "☁", // Mostly Cloudy - Cloud Emoji
        2000 to "\uD83C\uDF2B", // Fog - Fog Emoji
        2100 to "Light Fog",
        3000 to "Light Wind",
        3001 to "\uD83C\uDF90", // Wind - Wind Chime Emoji
        3002 to "\uD83C\uDF2C️", // Strong Wind - Wind Face Emoji
        4000 to "Drizzle",
        4001 to "\uD83C\uDF27", // Rain - Cloud with Rain Emoji
        4200 to "☂️", // Light Rain - Umbrella Emoji
        4201 to "☔", // Heavy Rain - Umbrella with Rain Drops Emoji
        5000 to "\uD83C\uDF28", // Snow - Cloud with Snow Emoji
        5001 to "❄️", // Flurries - Snowflake Emoji
        5100 to "Light Snow",
        5101 to "Heavy Snow",
        6000 to "Freezing Drizzle",
        6001 to "Freezing Rain",
        6200 to "Light Freezing Rain",
        6201 to "Heavy Freezing Rain",
        7000 to "Ice Pellets",
        7101 to "Heavy Ice Pellets",
        7102 to "Light Ice Pellets",
        8000 to "Thunderstorm"
    )

    private suspend fun fetchWeather(location: Location) {
        val url = "https://api.tomorrow.io/v4/timelines".toHttpUrl()
            .newBuilder()
            .addQueryParameter("location", "${location.latitude},${location.longitude}")
            .addQueryParameter("fields", "temperature,temperatureApparent,weatherCode")
            .addQueryParameter("units", "metric")
            .addQueryParameter("timesteps", "current")
            .build()
        val request = Request.Builder()
            .url(url)
            .header("apikey", "JmBDTRrzShBjeTmIh2TSgkuf6ERf1DRh")
            .header("content-type", "application/json")
            .build()
        val response: Response = client.newCall(request).executeAsync()

        try {
            val data = Json.decodeFromString<Data>(response.body!!.string())
            val values = data.data.timelines[0].intervals[0].values
            val weatherEmoji = weatherCodeToEmoji[values.weatherCode]
            val temperature = values.temperatureApparent.roundToInt()
            _weather.postValue("$weatherEmoji $temperature° C")
        } catch (ex: kotlinx.serialization.SerializationException) {
            Log.e(TAG, "Failed to parse weather response", ex)
        }
    }

    private suspend fun Call.executeAsync(): Response {
        return suspendCancellableCoroutine { continuation ->
            enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response)
                }

                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }
            })

            continuation.invokeOnCancellation {
                try {
                    cancel()
                } catch (ex: Throwable) {
                    Log.w(TAG, "Request cancel threw", ex)
                }
            }
        }
    }

    companion object {
        val TAG = "SharedViewModel"
    }
}