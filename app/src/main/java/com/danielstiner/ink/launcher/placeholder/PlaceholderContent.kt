package com.danielstiner.ink.launcher.placeholder

import android.graphics.drawable.Drawable
import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * A placeholder item representing a piece of content.
     */
    data class PlaceholderItem(
        val label: CharSequence,
        val packageName: CharSequence,
        val icon: Drawable
    )
}