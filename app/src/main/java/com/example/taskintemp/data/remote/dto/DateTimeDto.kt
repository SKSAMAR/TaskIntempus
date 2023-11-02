package com.example.taskintemp.data.remote.dto

import android.util.Log
import com.example.taskintemp.domain.model.DateModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DateTimeDto(
    val dateTime: String, // yyyy-MM-dd HH:mm
)

fun DateTimeDto.toDateModel(): DateModel {
    Log.d("READY_TYPECAST", dateTime+"")


    val detailedDateFormatter = SimpleDateFormat("dd MMMM yyyy")
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    val timeFormatter = SimpleDateFormat("HH:mm")

    val detailedDate = detailedDateFormatter.format(detailedDateFormatter.parse(dateTime))
    val date = dateFormatter.format(detailedDateFormatter.parse(dateTime))
    val time = timeFormatter.format(detailedDateFormatter.parse(dateTime))
    return DateModel(primaryDateTime = dateTime, detailedDate = detailedDate, date = date, time = time)
}