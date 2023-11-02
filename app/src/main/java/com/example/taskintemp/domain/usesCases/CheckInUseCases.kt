package com.example.taskintemp.domain.usesCases

import com.example.taskintemp.data.db.entity.Employee
import com.example.taskintemp.data.remote.dto.DateTimeDto
import com.example.taskintemp.domain.model.TimeValidation
import com.example.taskintemp.domain.repository.CheckInRepository
import com.example.taskintemp.util.AppUtils.getCurrentSystemDate
import com.example.taskintemp.util.AppUtils.getCurrentSystemDateTime
import com.example.taskintemp.util.NetworkResource
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

    fun getApiDateTime(): Flow<NetworkResource<DateTimeDto>> = flow {
        try {
            emit(NetworkResource.Loading())
            delay(2000L)
            val dataTimeDto = repository.getApiDateTime()
            emit(NetworkResource.Success(dataTimeDto))
        } catch (e: HttpException) {
            emit(NetworkResource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(
                NetworkResource.Error(
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection."
                )
            )
        }
    }

    fun getMockedApiDateTime(): Flow<NetworkResource<DateTimeDto>> = flow {
        //mock fake request
        try {
            emit(NetworkResource.Loading())
            delay(2000L)
            val date = getCurrentSystemDate()+" 06:30"
            val jsonResponse = "{\"dateTime\": \"$date\"}"
            val dataTimeDto = Gson().fromJson(jsonResponse, DateTimeDto::class.java)
            emit(NetworkResource.Success(dataTimeDto))
        } catch (e: Exception) {
            emit(NetworkResource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    fun validateSelectedTime(currentDate: String, hourOfDay: Int, minute: Int) : Flow<TimeValidation> = flow {
        try {

            if (hourOfDay > Calendar.getInstance()
                    .get(Calendar.HOUR_OF_DAY) || (hourOfDay == Calendar.getInstance()
                    .get(Calendar.HOUR_OF_DAY) && minute > Calendar.getInstance()
                    .get(Calendar.MINUTE))
            ) {
                emit(
                    TimeValidation.Error
                )
            } else {
                emit(
                    TimeValidation.SuccessfullyValidated(DateTimeDto("$currentDate $hourOfDay:$minute"))
                )
            }

        }catch (e: Exception){
            emit(
                TimeValidation.Error
            )
        }
    }

    suspend fun insertCheckIn(timeStamp: String) {
        repository.insertCheckIn(timeStamp)
    }

    fun getEmployeeList(): Flow<List<Employee>>  {
       return repository.getAllCheckInsRows()
    }

}