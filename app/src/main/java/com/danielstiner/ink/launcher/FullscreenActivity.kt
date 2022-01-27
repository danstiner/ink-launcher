package com.danielstiner.ink.launcher

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.danielstiner.ink.launcher.databinding.ActivityFullscreenBinding
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class FullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hide()

        // TODO refresh when date changes
        binding.dateText.text = android.text.format.DateFormat.getMediumDateFormat(this).format(Date())
        binding.dateText.setOnClickListener {
            startActivity(Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_CALENDAR).apply {
                addFlags(FLAG_ACTIVITY_NEW_TASK)
            })
        }

        binding.actionButton1.setOnClickListener {
            startActivity(Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_MAPS).apply {
                addFlags(FLAG_ACTIVITY_NEW_TASK)
            })
        }

        binding.actionButton3.setOnClickListener {
            startActivity(Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_MUSIC).apply {
                addFlags(FLAG_ACTIVITY_NEW_TASK)
            })
        }

        binding.bottomLeftButton.setOnClickListener {
            startActivity(Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_MESSAGING).apply {
                addFlags(FLAG_ACTIVITY_NEW_TASK)
            })
        }

        binding.bottomRightButton.setOnClickListener {
            startActivity(Intent.makeMainSelectorActivity(Intent.ACTION_DIAL, Intent.CATEGORY_DEFAULT).apply {
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