package com.danielstiner.ink.launcher.data

import android.content.Intent
import android.content.pm.PackageManager
import androidx.annotation.WorkerThread
import com.danielstiner.ink.launcher.data.model.AppItem
import com.danielstiner.ink.launcher.data.source.CategoryDataSource
import com.danielstiner.ink.launcher.data.source.LaunchHistoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(
    private val packageManager: PackageManager,
    private val categories: CategoryDataSource,
    private val launchHistory: LaunchHistoryDataSource
) {

    suspend fun get(packageName: String?, action: String?, category: String?): List<AppItem> {
        return withContext(Dispatchers.IO) {
            return@withContext queryIntent(
                Intent(
                    action,
                    null
                ).apply {
                    category?.let { addCategory(it) }
                    packageName?.let { `package` = packageName }
                }
            )
        }
    }

    suspend fun getAll(): List<AppItem>? {
        return withContext(Dispatchers.IO) {
            return@withContext queryAll()
        }
    }

    @WorkerThread
    private fun queryAll() =
        queryIntent(
            Intent(
                Intent.ACTION_MAIN,
                null
            ).apply { addCategory(Intent.CATEGORY_LAUNCHER) })

    @WorkerThread
    private fun queryIntent(intent: Intent): List<AppItem> =
        packageManager.queryIntentActivities(intent, 0).map { info ->
            AppItem(
                label = info.loadLabel(packageManager),
                packageName = info.activityInfo.packageName,
                category = categories.categorize(info),
            )
        }.sortedBy { app -> app.label.toString().lowercase() }

    @WorkerThread
    fun recent(limit: Int) = launchHistory.recent(limit)

    @WorkerThread
    fun mostUsed(limit: Int) =
        launchHistory.appsOrderedByCountDesc().take(limit)

    companion object {
        private const val TAG = "AppRepository"
    }
}