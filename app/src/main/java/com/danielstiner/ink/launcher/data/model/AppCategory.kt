package com.danielstiner.ink.launcher.data.model

/**
 * See https://developer.android.com/reference/android/content/pm/ApplicationInfo#category
 */
data class AppCategory(val id: Int, val label: String) {

    companion object {
        // Sub-categories of ApplicationInfo.CATEGORY_ACCESSIBILITY

        // Sub-categories of ApplicationInfo.CATEGORY_MUSIC
        val LISTEN = AppCategory(2, label = "Listen")

        // Sub-categories of ApplicationInfo.CATEGORY_GAME
        val GAME = AppCategory(3, label = "Game")

        // Sub-categories of ApplicationInfo.CATEGORY_IMAGE
        val CAPTURE = AppCategory(4, label = "Capture")

        // Sub-categories of ApplicationInfo.CATEGORY_MAPS
        val GO = AppCategory(5, label = "Go")

        // Sub-categories of ApplicationInfo.CATEGORY_NEWS

        // Sub-categories of

        // Sub-categories of ApplicationInfo.CATEGORY_SOCIAL
        val MESSAGE = AppCategory(8, label = "Message")
        val SOCIAL = AppCategory(9, label = "Socialize")

        // Sub-categories of ApplicationInfo.CATEGORY_UNDEFINED
        val UNDEFINED = AppCategory(11, label = "Other")
        val WORKOUT = AppCategory(12, label = "Workout")
        val LEARN = AppCategory(14, label = "Learn")
        val HIDE = AppCategory(15, label = "Hidden")
        val CALL = AppCategory(17, label = "Call")
        val PLAN = AppCategory(18, label = "Plan")
        val CREATE = AppCategory(1, label = "Create")

        // Sub-categories of ApplicationInfo.CATEGORY_VIDEO
        val WATCH = AppCategory(20, label = "Watch")

        val WELLBEING = AppCategory(6, label = "Well-being")

        // Aliases
        val ACCESSIBILITY = UNDEFINED
        val AUTHENTICATE = UNDEFINED
        val DATE = SOCIAL
        val NOTE = CAPTURE
        val BROWSE = UNDEFINED
        val STAY = PLAN
        val MEET = CALL
        val BANK = UNDEFINED
        val READ = UNDEFINED
        val COOK = UNDEFINED
        val UTILITY = UNDEFINED
        val OUTSIDE = GO
        val HIKE = WORKOUT
        val FLY = GO
        val NEWS = UNDEFINED

        // All
        val ALL = listOf(
            CALL,
            CAPTURE,
            CREATE,
            GAME,
            GO,
            LEARN,
            LISTEN,
            MESSAGE,
            PLAN,
            SOCIAL,
            UNDEFINED,
            WATCH,
            WELLBEING,
            WORKOUT,
        )
    }
}
