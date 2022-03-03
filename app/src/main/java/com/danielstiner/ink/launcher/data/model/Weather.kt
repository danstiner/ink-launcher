package com.danielstiner.ink.launcher.data.model

import java.time.Instant
import kotlin.math.roundToInt

data class Weather(
    val time: Instant,
    val temperature: Double,
    val temperatureApparent: Double,
    val textDescription: String?,
    val emojiDescription: String?
) {

    fun toDisplayString(): String {
        return "${temperature.roundToInt()}° $textDescription"
    }
}
