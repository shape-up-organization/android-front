package com.shapeup.ui.utils.helpers

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DateHelper(private val locale: Locale = Locale.getDefault()) {
    fun fromMillisToFormFieldDateString(value: Long): String {
        val formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, locale)
        formatter.timeZone = TimeZone.getTimeZone("UTC")

        return formatter.format(Date(value))
    }

    fun fromFormFieldDateStringToServiceDate(value: String, pattern: String = "MM/dd/yyyy"): String {
        val mediumPattern = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, locale)
        val desiredPattern = SimpleDateFormat(pattern, locale)

        val date = mediumPattern.parse(value)
        return desiredPattern.format(date!!)
    }
}
