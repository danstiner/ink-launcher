package com.danielstiner.ink.launcher

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
                )
            }.sortedBy { app -> app.label.toString() })
        }
    }
}