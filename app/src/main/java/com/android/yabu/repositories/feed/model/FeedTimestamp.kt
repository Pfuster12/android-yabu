package com.android.yabu.repositories.feed.model

import java.util.*

/**
 * Simple '{day}-{month}' string timestamp to check a feed's freshness.
 */
class FeedTimestamp {

    var day: Int = -1
    var month: Int = -1

    init {
        // initialize the day and month to today's date,
        val c = Calendar.getInstance()

        day = c.get(Calendar.DAY_OF_MONTH)
        month = c.get(Calendar.MONTH)
    }

    /**
     * Generates a [String] timestamp with the day-month format.
     */
    fun generateTimestamp() = "$day-$month"
}