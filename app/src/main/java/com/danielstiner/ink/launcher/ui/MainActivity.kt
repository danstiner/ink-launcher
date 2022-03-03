package com.danielstiner.ink.launcher.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.MotionEvent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.danielstiner.ink.launcher.databinding.ActivityMainBinding
import java.lang.reflect.Field
import java.util.*
import kotlin.time.ExperimentalTime


@ExperimentalTime
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

        val navHost = binding.navHostFragment
        val drawer = binding.drawer
        val navController = navHost.getFragment<NavHostFragment>().navController

        globalSwipeDetector = GlobalSwipeDetector(this, navController)

        supportActionBar?.hide()

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.i(TAG, "Approximate location access granted.")
                    viewModel.onLocationAccessGranted()
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

        drawer.setOnTouchListener(globalSwipeDetector)

        val timeFormat = DateFormat.getTimeFormat(this)
        binding.timeText.text = timeFormat.format(Date())

        viewModel.localDate.observe(this) { date ->
            binding.dateText.text = DateFormat.format("EE, MMM d", date)
        }
        binding.dateText.setOnClickListener {
            viewModel.launchSelector(
                Intent.ACTION_MAIN,
                Intent.CATEGORY_APP_CALENDAR,
                this
            )
        }

        // Show four most used apps, ascending order
        viewModel.mostUsed.observe(this) { mostUsed ->
            mostUsed.getOrNull(0)?.let { app ->
                binding.actionButton4.text = app.label
                binding.actionButton4.setOnClickListener {
                    drawer.closeDrawers()
                    viewModel.launchApp(app, this)
                }
            }

            mostUsed.getOrNull(1)?.let { app ->
                binding.actionButton3.text = app.label
                binding.actionButton3.setOnClickListener {
                    drawer.closeDrawers()
                    viewModel.launchApp(app, this)
                }
            }

            mostUsed.getOrNull(2)?.let { app ->
                binding.actionButton2.text = app.label
                binding.actionButton2.setOnClickListener {
                    drawer.closeDrawers()
                    viewModel.launchApp(app, this)
                }
            }

            mostUsed.getOrNull(3)?.let { app ->
                binding.actionButton1.text = app.label
                binding.actionButton1.setOnClickListener {
                    drawer.closeDrawers()
                    viewModel.launchApp(app, this)
                }
            }
        }

        binding.bottomLeftButton.setOnClickListener {
            viewModel.launchSelector(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER, this)
        }
        binding.bottomCenterButton.setOnClickListener {
            viewModel.launchSelector(Intent.ACTION_MAIN, Intent.CATEGORY_APP_MESSAGING, this)
        }
        binding.bottomRightButton.setOnClickListener {
            viewModel.launchIntent(
                Intent(Intent.ACTION_DIAL, Uri.parse("tel:")),
                this
            )
        }

        increaseEdgeSize(drawer)
    }

    private fun increaseEdgeSize(drawer: DrawerLayout) {
        val mLeftDragger: Field = drawer.javaClass.getDeclaredField("mLeftDragger")
        mLeftDragger.isAccessible = true
        val leftDragger = mLeftDragger.get(drawer)
        increaseEdgeSize(leftDragger)

        val mRightDragger: Field = drawer.javaClass.getDeclaredField("mRightDragger")
        mRightDragger.isAccessible = true
        val rightDragger = mRightDragger.get(drawer)
        increaseEdgeSize(rightDragger)
    }

    private fun increaseEdgeSize(dragger: Any) {
        val mDefaultEdgeSize: Field = dragger.javaClass.getDeclaredField("mDefaultEdgeSize")
        mDefaultEdgeSize.isAccessible = true

        val edgeSize = 8 * mDefaultEdgeSize.getInt(dragger)
        mDefaultEdgeSize.setInt(dragger, edgeSize)

        val mEdgeSize: Field = dragger.javaClass.getDeclaredField("mEdgeSize")
        mEdgeSize.isAccessible = true
        mEdgeSize.setInt(dragger, edgeSize)
    }

    override fun onTouchEvent(event: MotionEvent) =
        globalSwipeDetector.onTouchEvent(event) || super.onTouchEvent(event)

    companion object {
        private const val TAG = "MainActivity"
    }
}
