package com.example.taskintemp.domain.model

import com.example.taskintemp.data.remote.dto.DateTimeDto

data class DateModel(
    val primaryDateTime: String, // yyyy-MM-dd HH:mm
    val date: String, // yyyy-MM-dd
    val year: Int,// yyyy
    val month: Int, // MM
    val day: Int, // dd
    val time: String, // HH:mm
    val hour: Int, // HH:mm
    val minute: Int // HH:mm
)

fun DateModel.toDateDto(): DateTimeDto = DateTimeDto(dateTime = this.primaryDateTime)