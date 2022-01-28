package com.danielstiner.ink.launcher.model

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * See https://developer.android.com/reference/android/content/pm/ApplicationInfo#category
 */
data class AppCategory(val value: Int) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTitle(context: Context) = ApplicationInfo.getCategoryTitle(context, value)

    companion object {
        val MESSAGING = AppCategory(ApplicationInfo.CATEGORY_SOCIAL)
    }
}