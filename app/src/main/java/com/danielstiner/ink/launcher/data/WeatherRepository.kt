package com.danielstiner.ink.launcher.data

import com.danielstiner.ink.launcher.data.model.LatLng
import com.danielstiner.ink.launcher.data.model.Weather
import com.danielstiner.ink.launcher.data.source.TomorrowIo

class WeatherRepository {
    private val tomorrowIo = TomorrowIo()

    suspend fun fetch(location: LatLng): Weather? {
        return tomorrowIo.fetchWeather(location)
    }
}