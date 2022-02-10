package com.danielstiner.ink.launcher.data.source

import android.util.Log
import com.danielstiner.ink.launcher.data.model.LatLng
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class GeoIp(private val client: OkHttpClient = OkHttpClient()) {

    val format = Json { ignoreUnknownKeys = true }

    suspend fun fetch(): LatLng? {
        val url = "https://ipapi.co/json/".toHttpUrl()
        val request = Request.Builder()
            .url(url)
            .header("content-type", "application/json")
            .build()
        val response: Response = client.newCall(request).executeAsync()

        return try {
            val data = format.decodeFromString<Data>(response.body!!.string())
            LatLng(
                latitude = data.latitude,
                longitude = data.longitude
            )
        } catch (ex: kotlinx.serialization.SerializationException) {
            Log.e(TAG, "Failed to parse geo ip response", ex)
            null
        }
    }

    companion object {
        private const val TAG = "GeoIp"
    }

    @Serializable
    data class Data(
        val latitude: Double,
        val longitude: Double,
    )

}