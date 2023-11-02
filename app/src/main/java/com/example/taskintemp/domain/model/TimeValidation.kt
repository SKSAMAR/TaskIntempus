package com.example.taskintemp.domain.model

import com.example.taskintemp.data.remote.dto.DateTimeDto

sealed class TimeValidation(private val selectedTimeDate: DateTimeDto? = null) {
    object Error : TimeValidation()
    data class SuccessfullyValidated(val selectedTimeDate: DateTimeDto): TimeValidation(selectedTimeDate)
}