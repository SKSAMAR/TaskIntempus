package com.example.taskintemp.presentation.mainscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(private val useCases: CheckInUseCases) : BaseViewModel<DateModel>() {

    var invalidTimeMessage by mutableStateOf("")
    var operationLoading by mutableStateOf(false)
    var selectedTimeDto by mutableStateOf<DateTimeDto?>(null)
    var allCheckInsList = useCases.getEmployeeList()


    init {
        getMostRecentCheckIn()
        // getDateTimeFromApi()
    }

    private fun getDateTimeFromApi() {
//        useCases.getApiDateTime().onEach { enable when api endpoint becomes use full
        useCases.getMockedApiDateTime().onEach {
            when (it) {
                is Resource.Success -> {
                    selectedTimeDto = it.data
                    val dateModel = it.data?.toDateModel()
                    _state.value = ScreenState(receivedResponse = dateModel)
                    validateDateSelected(
                        dateSelected = dateModel?.date ?: "",
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

    fun validateDateSelected(dateSelected: String, hours: Int, minutes: Int) {
        when (val result = useCases.validateSelectedDateTime(dateSelected, hours, minutes)) {
            is TimeValidation.Error -> {
                invalidTimeMessage = "Future Date time is not allowed"
                selectedTimeDto = result.selectedTimeDate
            }

            is TimeValidation.SuccessfullyValidated -> {
                invalidTimeMessage = ""
                selectedTimeDto = result.selectedTimeDate
            }
        }
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


    private fun getMostRecentCheckIn() {
        useCases.getMostRecentCheckIn().onEach {
            when (it) {
                is Resource.Error -> {
                    getDateTimeFromApi()
                }

                is Resource.Loading -> {
                    _state.value = ScreenState(isLoading = true)
                }

                is Resource.Success -> {
                    val dateTimeDto = DateTimeDto(dateTime = it.data?.check_in_date ?: "")
                    selectedTimeDto = dateTimeDto
                    _state.value = ScreenState(receivedResponse = selectedTimeDto?.toDateModel())
                }
            }
        }.launchIn(viewModelScope)
    }
}