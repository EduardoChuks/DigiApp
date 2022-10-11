package com.edu.labs.digiapp.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    const val yyyy_MM_ddTHH_mm_ss_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    private const val NO_DATE = "No date registered"
    private const val NOW = "Now"
    private const val YESTERDAY = "Yesterday"
    private const val UTC_FORMAT = "UTC"

    private const val ONE_MIN_IN_SECS: Long = 60
    private const val ONE_HOUR_IN_SECS: Long = 60 * 60
    private const val ONE_DAY_IN_SECS: Long = 60 * 60 * 24
    private const val TWO_DAYS_IN_SECS: Long = 60 * 60 * 24 * 2
    
    fun parseDate(parsingPattern: String, date: String?): Date? {
        return parseDate(parsingPattern, date, false)
    }

    @SuppressLint("SimpleDateFormat")
    fun parseDate(parsingPattern: String, date: String?, shouldSetTimeZone: Boolean): Date? {
        return if (!date.isNullOrEmpty()) {
            try {
                val sdf = SimpleDateFormat(parsingPattern)
                if (shouldSetTimeZone) sdf.timeZone = TimeZone.getTimeZone(UTC_FORMAT)
                sdf.parse(date)
            } catch (e: ParseException) {
                null
            }
        } else null
    }

    fun formatArticleDate(date: Date?): String {
        if (date != null) {
            val now = Calendar.getInstance().time
            val diffInTime: Long = now.time - date.time
            val diffInSecs: Long = diffInTime / 1000
            if (diffInSecs > TWO_DAYS_IN_SECS) return "${diffInSecs / TWO_DAYS_IN_SECS}d"
            if (diffInSecs > ONE_DAY_IN_SECS) return YESTERDAY
            if (diffInSecs > ONE_HOUR_IN_SECS) return "${diffInSecs / ONE_HOUR_IN_SECS}h"
            if (diffInSecs > ONE_MIN_IN_SECS) return "${diffInSecs / ONE_MIN_IN_SECS}m"
            if (diffInSecs > 0) return "${diffInSecs}s"
            return NOW
        } else return NO_DATE
    }

}