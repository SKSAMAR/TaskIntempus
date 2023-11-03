package com.example.taskintemp.domain.usesCases

import android.annotation.SuppressLint
import com.example.taskintemp.data.db.entity.Employee
import com.example.taskintemp.data.remote.dto.DateTimeDto
import com.example.taskintemp.domain.model.TimeValidation
import com.example.taskintemp.domain.repository.CheckInRepository
import com.example.taskintemp.util.AppUtils.getCurrentSystemDate
import com.example.taskintemp.util.AppUtils.getCurrentSystemDateTime
import com.example.taskintemp.util.Constants.TIME_STAMP_FORMAT
import com.example.taskintemp.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class CheckInUseCases
@Inject constructor(private val repository: CheckInRepository) {

    fun getApiDateTime(): Flow<Resource<DateTimeDto>> = flow {
        try {
            emit(Resource.Loading())
            delay(500L) // faking wait time and must be removed after getting a working api
            withContext(Dispatchers.IO){
                val dataTimeDto = repository.getApiDateTime()
                emit(Resource.Success(dataTimeDto))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    e.localizedMessage
                        ?: "Couldn't reach server. Check your internet connection."
                )
            )
        }
    }

    fun getMockedApiDateTime(): Flow<Resource<DateTimeDto>> = flow {
        //mock fake request
        try {
            emit(Resource.Loading())
            delay(2000L)
            val date = getCurrentSystemDate() + " 06:30"
            val jsonResponse = "{\"dateTime\": \"$date\"}"
            val dataTimeDto = Gson().fromJson(jsonResponse, DateTimeDto::class.java)
            emit(Resource.Success(dataTimeDto))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun validateSelectedDateTime(
        currentDate: String,
        hourOfDay: Int,
        minute: Int
    ): TimeValidation {

        val fixedHour = if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"
        val fixedMinutes = if (minute < 10) "0$minute" else "$minute"
        val currentTimeStamp = "$currentDate $fixedHour:$fixedMinutes"
        val systemDate = getCurrentSystemDateTime()

        // Convert the string dates to Date objects
        val sysTime = try {
            SimpleDateFormat(TIME_STAMP_FORMAT).parse(systemDate)
        } catch (e: ParseException) {
            Date()
        }
        val currTime = try {
            SimpleDateFormat(TIME_STAMP_FORMAT).parse(currentTimeStamp)
        } catch (e: ParseException) {
            Date()
        }
        val diff = currTime.compareTo(sysTime)
        return if (diff < 1){
            TimeValidation.SuccessfullyValidated(DateTimeDto("$currentDate $fixedHour:$fixedMinutes"))
        }  else{
            TimeValidation.Error(DateTimeDto("$currentDate $fixedHour:$fixedMinutes"))
        }

    }


    suspend fun insertCheckIn(timeStamp: String) {
        // more logic will be written in case
        withContext(Dispatchers.IO) {
            repository.insertCheckIn(timeStamp)
        }
    }

    fun getEmployeeList(): Flow<List<Employee>> {
        // more logic will be written in case
        return repository.getAllCheckInsRows()
    }

    fun getEmployeeByTimestamp(timeStamp: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getEmployeeByTimestamp(timeStamp)
            if (response == null) {
                emit(Resource.Success(""))
            } else {
                emit(Resource.Error("Current Time is Already Checked In"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    fun getMostRecentCheckIn(): Flow<Resource<Employee?>> = flow {
            try {
                emit(Resource.Loading())
                val response = repository.getMostRecentCheckIn()
                if (response != null) {
                    emit(Resource.Success(response))
                } else {
                    emit(Resource.Error("No Recent Check In Found"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
    }

}