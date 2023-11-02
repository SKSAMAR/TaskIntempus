package com.example.taskintemp.domain.usesCases

import com.example.taskintemp.data.remote.dto.DateTimeDto
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

}