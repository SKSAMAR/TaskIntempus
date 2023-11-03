package com.example.taskintemp.data.remote.dto

import com.example.taskintemp.domain.model.DateModel

data class DateTimeDto(
    val dateTime: String, // yyyy-MM-dd HH:mm
)

fun DateTimeDto.toDateModel(): DateModel {
    val (date, time) = dateTime.split(" ")
    val (hour, minute) = time.split(":")

    return DateModel(
        primaryDateTime = dateTime,
        date = date,
        time = time,
        hour = hour.toInt(),
        minute = minute.toInt()
    )
}