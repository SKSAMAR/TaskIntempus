package com.example.taskintemp.presentation.mainscreen

import android.os.Build
import android.util.Log
import android.widget.TimePicker
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.taskintemp.data.db.entity.Employee
import com.example.taskintemp.data.remote.dto.DateTimeDto
import com.example.taskintemp.data.remote.dto.toDateModel
import com.example.taskintemp.domain.model.DateModel
import com.example.taskintemp.domain.model.ScreenState
import com.example.taskintemp.domain.model.TimeValidation
import com.example.taskintemp.domain.usesCases.CheckInUseCases
import com.example.taskintemp.presentation.common.BaseViewModel
import com.example.taskintemp.util.AppUtils.isSafeClick
import com.example.taskintemp.util.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(private val useCases: CheckInUseCases) : BaseViewModel<DateModel>() {


    var invalidTimeMessage by mutableStateOf("")
    private var selectedTimeDto: DateTimeDto? = null
    private var allCheckIns by mutableStateOf(emptyList<Employee>())

    init {
        getDateTimeFromApi()
        fetchAllCheckIns()
    }

    private fun getDateTimeFromApi() {
//        useCases.getApiDateTime().onEach { enable when api endpoint becomes use full
        useCases.getMockedApiDateTime().onEach {
            when (it) {
                is NetworkResource.Success -> {
                    _state.value = ScreenState(receivedResponse = it.data?.toDateModel())
                }

                is NetworkResource.Loading -> {
                    _state.value = ScreenState(isLoading = true)
                }

                is NetworkResource.Error -> {
                    _state.value = ScreenState(error = it.message ?: "Some Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun modifyTimePicker(timePicker: TimePicker) {
        timePicker.apply {
            setIs24HourView(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hour = state.value.receivedResponse?.hour ?: 0
                minute = state.value.receivedResponse?.minute ?: 0
            } else {
                currentHour = state.value.receivedResponse?.hour ?: 0
                currentMinute = state.value.receivedResponse?.minute ?: 0
            }
        }
        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            validateTimeSelected(hourOfDay, minute)
        }
    }

    private fun validateTimeSelected(hours: Int, minutes: Int) {
        useCases.validateSelectedTime(state.value.receivedResponse?.date ?: "", hours, minutes)
            .onEach {
                when (it) {
                    TimeValidation.Error -> {
                        invalidTimeMessage = "Time cannot be greater than current system time"
                        selectedTimeDto = null
                    }

                    is TimeValidation.SuccessfullyValidated -> {
                        invalidTimeMessage = ""
                        selectedTimeDto = it.selectedTimeDate
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun addCheckIn() {
        if (isSafeClick()) {
            viewModelScope.launch(Dispatchers.IO) {
                selectedTimeDto?.dateTime?.let {
                    useCases.insertCheckIn(it)
                }
            }
        }
    }

    private fun fetchAllCheckIns(){
        viewModelScope.launch(Dispatchers.IO){
            useCases.getEmployeeList().collectLatest {
                allCheckIns = it
            }
        }
    }
}