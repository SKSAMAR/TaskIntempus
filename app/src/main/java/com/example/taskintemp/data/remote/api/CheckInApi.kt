package com.example.taskintemp.data.remote.api

import com.example.taskintemp.data.remote.dto.DateTimeDto
import com.example.taskintemp.util.Constants
import retrofit2.http.GET

interface CheckInApi {

    @GET(Constants.GET_DATE_TIME)
    suspend fun getDateTime(): DateTimeDto

}