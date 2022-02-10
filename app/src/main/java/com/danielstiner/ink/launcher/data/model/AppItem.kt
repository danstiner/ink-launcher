package com.danielstiner.ink.launcher.data.model

import android.content.Context
import android.content.Intent

data class AppItem(
    val label: CharSequence,
    val packageName: CharSequence,
    val category: AppCategory,
)


data class PackageLaunch(val packageName: String) : Launchable {
    override fun makeLaunchIntent(context: Context) =
        context.packageManager.getLaunchIntentForPackage(packageName)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
}

data class SelectorLaunch(val action: String, val category: String) : Launchable {
    override fun makeLaunchIntent(context: Context) =
        Intent.makeMainSelectorActivity(action, category).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
}

interface Launchable {
    fun makeLaunchIntent(context: Context): Intent?
}
