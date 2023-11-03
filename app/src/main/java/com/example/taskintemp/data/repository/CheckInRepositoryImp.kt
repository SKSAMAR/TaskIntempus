package com.example.taskintemp.data.repository

import com.example.taskintemp.data.db.dao.EmployeeDao
import com.example.taskintemp.data.db.entity.Employee
import com.example.taskintemp.data.remote.api.CheckInApi
import com.example.taskintemp.data.remote.dto.DateTimeDto
import com.example.taskintemp.data.remote.dto.toDateModel
import com.example.taskintemp.domain.model.DateModel
import com.example.taskintemp.domain.repository.CheckInRepository
import com.example.taskintemp.util.AppUtils.getCurrentSystemDateTime
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckInRepositoryImp
@Inject constructor(
    private val api: CheckInApi,
    private val employeeDao: EmployeeDao
) : CheckInRepository {

    override suspend fun getApiDateTime(): DateTimeDto {
        return api.getDateTime()
    }

    override suspend fun insertCheckIn(check_in_date: String) {
       employeeDao.insertCheckInTime(employee = Employee(check_in_date = check_in_date))
    }

    override fun getCurrentDateTimeModel(): DateModel {
        val primaryDateTime = getCurrentSystemDateTime()
        return DateTimeDto(primaryDateTime).toDateModel()
    }

    override suspend fun getEmployeeByTimestamp(check_in_date: String): Employee? {
        return employeeDao.getEmployeeByTimestamp(check_in_date)
    }

    override fun getAllCheckInsRows(): Flow<List<Employee>> {
        return employeeDao.getAllCheckInTimes()
    }
}