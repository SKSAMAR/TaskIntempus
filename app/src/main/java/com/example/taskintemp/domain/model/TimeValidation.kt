package com.example.taskintemp.domain.model

import com.example.taskintemp.data.remote.dto.DateTimeDto

sealed class TimeValidation(private val selectedTimeDate: DateTimeDto? = null) {
    data class Error(val selectedTimeDate: DateTimeDto) : TimeValidation(selectedTimeDate)
    data class SuccessfullyValidated(val selectedTimeDate: DateTimeDto): TimeValidation(selectedTimeDate)
}