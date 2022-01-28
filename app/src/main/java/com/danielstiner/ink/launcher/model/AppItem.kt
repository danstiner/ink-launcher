package com.danielstiner.ink.launcher.model

data class AppItem(
    val label: CharSequence,
    val packageName: CharSequence,
    val category: AppCategory,
)