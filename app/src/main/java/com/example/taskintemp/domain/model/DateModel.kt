package com.example.taskintemp.domain.model

import com.example.taskintemp.data.remote.dto.DateTimeDto

data class DateModel(
    val primaryDateTime: String, // yyyy-MM-dd HH:mm
    val detailedDate: String, // dd MMMM yyyy
    val date: String, // yyyy-MM-dd
    val time: String // HH:mm
)

fun DateModel.toDateDto(): DateTimeDto = DateTimeDto(dateTime = this.primaryDateTime)