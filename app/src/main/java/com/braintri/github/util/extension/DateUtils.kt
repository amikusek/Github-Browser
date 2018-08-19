package com.braintri.github.util.extension

import java.text.SimpleDateFormat
import java.util.*

const val DATE_WITH_TIME_FORMAT = "dd.MM.yyyy HH:mm"

fun mapRemoteDateToLocal(date: String?): Date {
    return try {
        val calendar = Calendar.getInstance().apply { time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault()).parse(date) }
        calendar.time
    } catch (exception: Exception) {
        Date()
    }
}

fun Date.getFormattedString(format: String): String {
    val sdf = SimpleDateFormat(format, Locale.ENGLISH)
    return sdf.format(this).toString()
}
