package com.example.taskintemp.domain.usesCases

import com.example.taskintemp.data.db.entity.Employee
import com.example.taskintemp.data.remote.dto.DateTimeDto
import com.example.taskintemp.domain.model.TimeValidation
import com.example.taskintemp.domain.repository.CheckInRepository
import com.example.taskintemp.util.AppUtils.getCurrentSystemDate
import com.example.taskintemp.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.Calendar
import javax.inject.Inject

class CheckInUseCases
@Inject constructor(private val repository: CheckInRepository) {

    fun getApiDateTime(): Flow<Resource<DateTimeDto>> = flow {
        try {
            emit(Resource.Loading())
            delay(2000L)
            val dataTimeDto = repository.getApiDateTime()
            emit(Resource.Success(dataTimeDto))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection."
                )
            )
        }
    }

    fun getMockedApiDateTime(): Flow<Resource<DateTimeDto>> = flow {
        //mock fake request
        try {
            emit(Resource.Loading())
            delay(2000L)
            val date = getCurrentSystemDate()+" 06:30"
            val jsonResponse = "{\"dateTime\": \"$date\"}"
            val dataTimeDto = Gson().fromJson(jsonResponse, DateTimeDto::class.java)
            emit(Resource.Success(dataTimeDto))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    fun validateSelectedTime(currentDate: String, hourOfDay: Int, minute: Int) : Flow<TimeValidation> = flow {
        val fixedHour = if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"
        val fixedMinutes = if (minute < 10) "0$minute" else "$minute"
        try {
            if (hourOfDay > Calendar.getInstance().get(Calendar.HOUR_OF_DAY) || (hourOfDay == Calendar.getInstance()
                .get(Calendar.HOUR_OF_DAY) && minute > Calendar.getInstance().get(Calendar.MINUTE))
            ) {
                emit(
                    TimeValidation.Error(DateTimeDto("$currentDate $fixedHour:$fixedMinutes"))
                )
            } else {
                emit(
                    TimeValidation.SuccessfullyValidated(DateTimeDto("$currentDate $fixedHour:$fixedMinutes"))
                )
            }
        }catch (e: Exception){
            emit(
                TimeValidation.Error(DateTimeDto("$currentDate $fixedHour:$fixedMinutes"))
            )
        }
    }

    suspend fun insertCheckIn(timeStamp: String) {
        // more logic will be written in case
        repository.insertCheckIn(timeStamp)
    }

    fun getEmployeeList(): Flow<List<Employee>>  {
        // more logic will be written in case
        return repository.getAllCheckInsRows()
    }

    fun getEmployeeByTimestamp(timeStamp: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getEmployeeByTimestamp(timeStamp)
            if (response == null){
                emit(Resource.Success(""))
            } else {
                emit(Resource.Error("Current Time is Already Checked In"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}