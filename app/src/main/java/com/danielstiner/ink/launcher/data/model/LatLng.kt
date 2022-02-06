package com.danielstiner.ink.launcher.data.model

import android.location.Location

data class LatLng(val latitude: Double, val longitude: Double)

fun Location.toLatLng(): LatLng = LatLng(latitude = latitude, longitude = longitude)

