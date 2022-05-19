package com.danielstiner.ink.launcher.data.model

/**
 * See https://developer.android.com/reference/android/content/pm/ApplicationInfo#category
 */
data class AppCategory(val id: Int, val label: String) {

    companion object {
        // Categories
        val CREATE = AppCategory(1, label = "Create")
        val LISTEN = AppCategory(2, label = "Listen")

        // Unused 3 was AppCategory(3, label = "Play")
        // Unused 4 was AppCategory(4, label = "Capture")
        val EXPLORE = AppCategory(5, label = "Explore")

        // Unused 6 - was AppCategory(6, label = "Health")
        // Unused 7
        //val MESSAGE = AppCategory(8, label = "Message")
        val SOCIAL = AppCategory(9, label = "Socialize")

        // Unused 10
        val UNDEFINED = AppCategory(11, label = "Other")
        val WORKOUT = AppCategory(12, label = "Workout")

        // Unused 13
        val LEARN = AppCategory(14, label = "Learn")
        val HIDDEN = AppCategory(15, label = "Hidden")

        // Unused 16
        //val CALL = AppCategory(17, label = "Call")
        // Unused 18 - was  AppCategory(18, label = "Plan")
        // Unused 19
        // Unused 20 was AppCategory(20, label = "Watch")
        val PRODUCTIVITY = AppCategory(21, label = "Productivity")
        val CHAT = AppCategory(22, label = "Chat")

        // Aliases
        val CALL = CHAT
        val MESSAGE = CHAT
        val PLAN = PRODUCTIVITY
        val CAPTURE = CREATE
        val ACCESSIBILITY = UNDEFINED
        val AUTHENTICATE = UNDEFINED
        val BANK = UNDEFINED
        val BROWSE = UNDEFINED
        val COOK = UNDEFINED
        val DATE = SOCIAL
        val FLY = EXPLORE
        val HEALTH = WORKOUT
        val HIKE = WORKOUT
        val MEET = CALL
        val NEWS = UNDEFINED
        val NOTE = CAPTURE
        val OUTSIDE = EXPLORE
        val READ = UNDEFINED
        val STAY = UNDEFINED
        val UTILITY = UNDEFINED
        val WATCH = UNDEFINED
        val PLAY = UNDEFINED

        // All categories in alphabetical order
        val ALL = listOf(
            CHAT,
            CREATE,
            EXPLORE,
            LEARN,
            LISTEN,
            PRODUCTIVITY,
            SOCIAL,
            UNDEFINED,
            WORKOUT,
        )
    }
}
