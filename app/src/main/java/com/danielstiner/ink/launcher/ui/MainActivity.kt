package com.danielstiner.ink.launcher.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.NavHostFragment
import com.danielstiner.ink.launcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by viewModels {
        SharedViewModelFactory(this)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var globalSwipeDetector: GlobalSwipeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        globalSwipeDetector = GlobalSwipeDetector(
            this,
            binding.root.getFragment<NavHostFragment>().navController
        )

        supportActionBar?.hide()

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.i(TAG, "Approximate location access granted.")
                    viewModel.fetchWeather()
                }
                else -> {
                    Log.w(TAG, "No location access granted")
                }
            }
        }

        // Before doing the actual permission request, check if we already have the permission
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent) =
        globalSwipeDetector.onTouchEvent(event) || super.onTouchEvent(event)

    companion object {
        private const val TAG = "MainActivity"
    }
}
