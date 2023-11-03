package com.example.taskintemp.domain.repository

import com.example.taskintemp.data.db.entity.Employee
import com.example.taskintemp.data.remote.dto.DateTimeDto
import com.example.taskintemp.domain.model.DateModel
import kotlinx.coroutines.flow.Flow

interface CheckInRepository {

    suspend fun getApiDateTime(): DateTimeDto
    suspend fun insertCheckIn(check_in_date: String)
    fun getCurrentDateTimeModel(): DateModel
    suspend fun getEmployeeByTimestamp(check_in_date: String): Employee?
    suspend fun getMostRecentCheckIn(): Employee?
    fun getAllCheckInsRows(): Flow<List<Employee>>

}