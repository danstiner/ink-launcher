package com.danielstiner.ink.launcher.data.model

data class AppItem(
    val label: CharSequence,
    val packageName: CharSequence,
    val category: AppCategory,
)