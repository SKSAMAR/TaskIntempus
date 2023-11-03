package com.example.taskintemp.presentation.mainscreen

import androidx.compose.runtime.getValue
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
import com.example.taskintemp.util.Resource
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
    var operationLoading by mutableStateOf(false)
    var selectedTimeDto by mutableStateOf<DateTimeDto?>(null)
    var allCheckInsList by mutableStateOf(emptyList<Employee>())

    init {
        fetchAllCheckIns()
        getDateTimeFromApi()
    }

    private fun getDateTimeFromApi() {
//        useCases.getApiDateTime().onEach { enable when api endpoint becomes use full
        useCases.getMockedApiDateTime().onEach {
            when (it) {
                is Resource.Success -> {
                    selectedTimeDto = it.data
                    val dateModel = it.data?.toDateModel()
                    _state.value = ScreenState(receivedResponse = dateModel)
                    validateTimeSelected(
                        hours = dateModel?.hour ?: 0,
                        minutes = dateModel?.minute ?: 0
                    )
                }

                is Resource.Loading -> {
                    _state.value = ScreenState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = ScreenState(error = it.message ?: "Some Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun validateTimeSelected(hours: Int, minutes: Int) {
        useCases.validateSelectedTime(state.value.receivedResponse?.date ?: "", hours, minutes)
            .onEach {
                when (it) {
                    is TimeValidation.Error -> {
                        invalidTimeMessage = "Time cannot be greater than current system time"
                        selectedTimeDto = it.selectedTimeDate
                    }

                    is TimeValidation.SuccessfullyValidated -> {
                        invalidTimeMessage = ""
                        selectedTimeDto = it.selectedTimeDate
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun addCheckIn() {
        if (isSafeClick() && invalidTimeMessage.isEmpty()) {
            selectedTimeDto?.dateTime?.let { dateTime ->
                useCases.getEmployeeByTimestamp(dateTime).onEach {
                    when (it) {
                        is Resource.Error -> {
                            operationLoading = false
                            invalidTimeMessage = it.message ?: ""
                        }

                        is Resource.Loading -> {
                            operationLoading = true
                        }
                        is Resource.Success -> {
                            operationLoading = false
                            invalidTimeMessage = ""
                            useCases.insertCheckIn(dateTime)
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun fetchAllCheckIns() {
        viewModelScope.launch(Dispatchers.Main) {
            useCases.getEmployeeList().collectLatest {
                allCheckInsList = it
            }
        }
    }
}