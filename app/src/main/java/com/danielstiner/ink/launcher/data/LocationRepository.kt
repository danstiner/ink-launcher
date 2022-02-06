package com.danielstiner.ink.launcher.data

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat.checkSelfPermission
import com.danielstiner.ink.launcher.data.model.LatLng
import com.danielstiner.ink.launcher.data.model.toLatLng
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationRepository(private val context: Context) {
    private val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

    suspend fun lastLocation(): LatLng? {
        if (checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            Log.d(TAG, "Location permission not granted")
            return null
        }

        // TODO use geo ip fallback, also cache location with a timeout

        return suspendCoroutine { continuation ->
            fusedLocationProvider.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    continuation.resume(location?.toLatLng())
                }
        }
    }

    companion object {
        private const val TAG = "LocationRepository"
    }

}
