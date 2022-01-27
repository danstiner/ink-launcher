package com.danielstiner.ink.launcher

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.danielstiner.ink.launcher.databinding.ActivityMainBinding
import com.danielstiner.ink.launcher.drawer.app.AppDrawerFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(this)
    }

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hide()

        // TODO refresh when date changes
        binding.dateText.text =
            android.text.format.DateFormat.getMediumDateFormat(this).format(Date())
        binding.dateText.setOnClickListener {
            startActivity(
                Intent.makeMainSelectorActivity(
                    Intent.ACTION_MAIN,
                    Intent.CATEGORY_APP_CALENDAR
                ).apply {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                })
        }

        binding.weatherText.setOnClickListener {
            startActivity(packageManager.getLaunchIntentForPackage("co.climacell.climacell"))
        }

        binding.actionButton1.setOnClickListener {
            startActivity(
                Intent.makeMainSelectorActivity(
                    Intent.ACTION_MAIN,
                    Intent.CATEGORY_APP_MAPS
                ).apply {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                })
        }

        binding.actionButton2.setOnClickListener {
            startActivity(packageManager.getLaunchIntentForPackage("com.amazon.kindle"))
        }

        binding.actionButton3.setOnClickListener {
            startActivity(
                Intent.makeMainSelectorActivity(
                    Intent.ACTION_MAIN,
                    Intent.CATEGORY_APP_MUSIC
                ).apply {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                })
        }

        binding.actionButton4.setOnClickListener {
            startActivity(packageManager.getLaunchIntentForPackage("com.google.android.keep"))
        }

        binding.bottomLeftButton.setOnClickListener {
            startActivity(
                Intent.makeMainSelectorActivity(
                    Intent.ACTION_MAIN,
                    Intent.CATEGORY_APP_MESSAGING
                ).apply {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                })
        }

        val appDrawerFragment = AppDrawerFragment()
        binding.bottomCenterButton.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, appDrawerFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.bottomRightButton.setOnClickListener {
            startActivity(
                Intent.makeMainSelectorActivity(
                    Intent.ACTION_DIAL,
                    Intent.CATEGORY_DEFAULT
                ).apply {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                })
        }
    }

    private fun hide() {
        supportActionBar?.hide()
        ViewCompat.getWindowInsetsController(window.decorView)?.apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }
    }
}