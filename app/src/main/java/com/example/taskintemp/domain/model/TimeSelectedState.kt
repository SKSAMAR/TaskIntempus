package com.example.taskintemp.domain.model

import com.example.taskintemp.data.remote.dto.DateTimeDto

data class TimeSelectedState(
    val invalidTimeMessage: String = "",
    val timeDto: DateTimeDto? = null
)
