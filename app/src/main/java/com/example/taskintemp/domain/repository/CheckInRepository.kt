package com.example.taskintemp.domain.repository

import com.example.taskintemp.data.remote.dto.DateTimeDto
import com.example.taskintemp.domain.model.DateModel

interface CheckInRepository {

    suspend fun getApiDateTime(): DateTimeDto

    fun getCurrentDateTimeModel(): DateModel

}