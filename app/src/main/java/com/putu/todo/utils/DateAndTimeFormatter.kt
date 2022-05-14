package com.putu.todo.utils

import java.util.*

fun currentDateAndTime(): Pair<String, String> {
    val c = Calendar.getInstance()

    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)

    val date = "$day/${month + 1}/$year"
    val time = "$hour:$minute"

    return date to time
}

fun fDate(date: String): String {
    return try {
        val old = date.split("/")
        "${String.format("%02d", old[0].toInt())}/${String.format("%02d", old[1].toInt())}/${old[2]}"
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun fTime(time: String): String {
    return try {
        val old = time.split(":")
        "${String.format("%02d", old[0].toInt())}:${String.format("%02d", old[1].toInt())}"
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}