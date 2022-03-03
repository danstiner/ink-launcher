package com.danielstiner.ink.launcher.data.source

import android.util.Log
import com.danielstiner.ink.launcher.data.model.LatLng
import com.danielstiner.ink.launcher.data.model.Weather
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.time.Instant

class NationalWeatherService(private val client: OkHttpClient = OkHttpClient()) {

    val format = Json { ignoreUnknownKeys = true }

    suspend fun fetchWeather(location: LatLng): Weather? {
        return gridPoint(location)?.let { hourlyForecast(it) }
    }

    private suspend fun hourlyForecast(point: GridPoint): Weather? {
        val url = "https://api.weather.gov/".toHttpUrl()
            .newBuilder()
            .addPathSegment(point.id)
            .addPathSegment("${point.x},${point.y}")
            .addPathSegment("forecast")
            .addPathSegment("hourly")
            .addQueryParameter("units", "metric")
            .build()
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "(Ink Launcher, daniel.stiner+inklauncher@gmail.com)")
            .header("Accept", "application/geo+json")
            .build()
        val response: Response = client.newCall(request).executeAsync()

        try {
            val data = format.decodeFromString<HourlyResponse>(response.body!!.string())
            val firstPeriod = data.properties.periods.first()
            return Weather(
                time = Instant.now(), // TODO use start end time of period
                temperature = firstPeriod.temperature.toDouble(),
                temperatureApparent = firstPeriod.temperature.toDouble(),
                textDescription = firstPeriod.shortForecast,
                emojiDescription = firstPeriod.shortForecast
            )
        } catch (ex: kotlinx.serialization.SerializationException) {
            Log.e(TAG, "Failed to parse weather response", ex)
            return null
        }
    }

    private suspend fun gridPoint(location: LatLng): GridPoint? {
        val url = "https://api.weather.gov/points".toHttpUrl()
            .newBuilder()
            .addPathSegment("${location.latitude},${location.longitude}")
            .build()
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "(Ink Launcher, daniel.stiner+inklauncher@gmail.com)")
            .header("Accept", "application/geo+json")
            .build()
        val response: Response = client.newCall(request).executeAsync()

        try {
            val data = format.decodeFromString<PointResponse>(response.body!!.string())
            return GridPoint(
                id = data.properties.gridId,
                x = data.properties.gridX,
                y = data.properties.gridY
            )
        } catch (ex: kotlinx.serialization.SerializationException) {
            Log.e(TAG, "Failed to parse gridpoint response", ex)
            return null
        }
    }

    data class GridPoint(val id: String, val x: Int, val y: Int)

    @Serializable
    data class HourlyResponse(val properties: HourlyResponseProperties)

    @Serializable
    data class HourlyResponseProperties(val units: String, val periods: List<HourlyPeriod>)

    @Serializable
    data class HourlyPeriod(
        val number: Int,
        val name: String,
        val startTime: String,
        val endTime: String,
        val isDaytime: Boolean,
        val temperature: Int,
        val temperatureUnit: String,
        val windSpeed: String,
        val windDirection: String,
        val icon: String,
        val shortForecast: String,
        val detailedForecast: String
    )

    @Serializable
    data class PointResponse(val properties: PointResponseProperties)

    @Serializable
    data class PointResponseProperties(val gridId: String, val gridX: Int, val gridY: Int)

    companion object {
        private const val TAG = "NWS"
    }
}