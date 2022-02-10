package com.danielstiner.ink.launcher.data.source

import android.content.pm.PackageManager
import androidx.annotation.WorkerThread

class PackageDataSource(
    private val packageManager: PackageManager,
) {

    @WorkerThread
    fun getLabel(packageName: String): CharSequence? {
        val intent = packageManager.getLaunchIntentForPackage(packageName) ?: return null
        val info = packageManager.queryIntentActivities(intent, 0).singleOrNull() ?: return null
        return info.loadLabel(packageManager)
    }
}