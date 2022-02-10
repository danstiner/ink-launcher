package com.danielstiner.ink.launcher.data.source

import androidx.annotation.WorkerThread
import com.danielstiner.ink.launcher.data.db.Launch
import com.danielstiner.ink.launcher.data.db.LaunchDao
import com.danielstiner.ink.launcher.data.model.AppCategory
import com.danielstiner.ink.launcher.data.model.AppItem

class LaunchHistoryDataSource(
    private val launches: LaunchDao,
    private val categories: CategoryDataSource,
    private val packageDataSource: PackageDataSource,
) {

    @WorkerThread
    fun recent(limit: Int): List<AppItem> {
        return launches.getRecent(limit)
            .map { launch -> ai(launch) }
    }

    @WorkerThread
    fun appsOrderedByCountDesc(): List<AppItem> {
        return launches.getAll()
            .map { launch -> ai(launch) }
            .sortedByCountDescending()
    }

    @WorkerThread
    fun categoriesOrderedByCountDesc(): List<AppCategory> {
        return launches.getAll()
            .map { launch -> categories.categorize(launch.packageName) }
            .sortedByCountDescending()
    }

    private fun ai(launch: Launch) =
        AppItem(
            label = packageDataSource.getLabel(launch.packageName) ?: "unknown",
            packageName = launch.packageName,
            category = categories.categorize(launch.packageName),
        )

}

private fun <T> List<T>.sortedByCountDescending(): List<T> =
    groupBy { id -> id }
        .mapValues { group -> group.value.count() }
        .entries
        .sortedByDescending { p -> p.value }
        .map { p -> p.key }
