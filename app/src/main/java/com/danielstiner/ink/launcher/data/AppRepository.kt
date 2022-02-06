package com.danielstiner.ink.launcher.data

import android.content.Intent
import android.content.pm.PackageManager
import androidx.annotation.WorkerThread
import com.danielstiner.ink.launcher.data.model.AppItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(
    private val packageManager: PackageManager,
    private val categoryRepository: CategoryRepository
) {

    suspend fun get(): List<AppItem>? {
        return withContext(Dispatchers.IO) {
            return@withContext queryAll()
        }
    }

    @WorkerThread
    private fun queryAll(): List<AppItem> {
        val intent =
            Intent(Intent.ACTION_MAIN, null).apply { addCategory(Intent.CATEGORY_LAUNCHER) }

        return packageManager.queryIntentActivities(intent, 0).map { info ->
            AppItem(
                label = info.loadLabel(packageManager),
                packageName = info.activityInfo.packageName,
                category = categoryRepository.categorize(info),
            )
        }.sortedBy { app -> app.label.toString().lowercase() }
    }

    companion object {
        private const val TAG = "AppRepository"
    }
}