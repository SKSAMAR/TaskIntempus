package com.example.taskintemp.data.remote.dto

import com.example.taskintemp.domain.model.DateModel

data class DateTimeDto(
    val dateTime: String, // yyyy-MM-dd HH:mm
)

fun DateTimeDto.toDateModel(): DateModel {
    val (date, time) = dateTime.split(" ")
    val (hour, minute) = time.split(":")

    val (year, month, day) = date.split("-")

    return DateModel(
        primaryDateTime = dateTime,
        date = date,
        year = year.toInt(),
        month = month.toInt(),
        day = day.toInt(),
        time = time,
        hour = hour.toInt(),
        minute = minute.toInt()
    )
}