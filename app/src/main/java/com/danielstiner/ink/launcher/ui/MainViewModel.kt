package com.danielstiner.ink.launcher.ui

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danielstiner.ink.launcher.model.AppCategory
import com.danielstiner.ink.launcher.model.AppItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(context: Context) : androidx.lifecycle.ViewModel() {

    private val packageManager = context.applicationContext.packageManager

    private val _apps = MutableLiveData<List<AppItem>>()
    val apps: LiveData<List<AppItem>> = _apps

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            val intent =
                Intent(Intent.ACTION_MAIN, null).apply { addCategory(Intent.CATEGORY_LAUNCHER) }

            _apps.postValue(packageManager.queryIntentActivities(intent, 0).map { info ->
                AppItem(
                    label = info.loadLabel(packageManager),
                    packageName = info.activityInfo.packageName,
                    category = category(info),
                )
            }.sortedBy { app -> app.label.toString() })
        }
    }

    private fun category(info: ResolveInfo): AppCategory =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AppCategory(info.activityInfo.applicationInfo.category)
        } else {
            AppCategory(ApplicationInfo.CATEGORY_UNDEFINED)
        }

}