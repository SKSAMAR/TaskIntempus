package com.example.taskintemp.data.repository

import com.example.taskintemp.data.remote.api.CheckInApi
import com.example.taskintemp.data.remote.dto.DateTimeDto
import com.example.taskintemp.data.remote.dto.toDateModel
import com.example.taskintemp.domain.model.DateModel
import com.example.taskintemp.domain.repository.CheckInRepository
import com.example.taskintemp.util.AppUtils.getCurrentSystemDateTime
import javax.inject.Inject

class CheckInRepositoryImp
@Inject constructor(private val api: CheckInApi) : CheckInRepository {

    override suspend fun getApiDateTime(): DateTimeDto {
        return api.getDateTime()
    }

    override fun getCurrentDateTimeModel(): DateModel {
        val primaryDateTime = getCurrentSystemDateTime()
        return DateTimeDto(primaryDateTime).toDateModel()
    }
}