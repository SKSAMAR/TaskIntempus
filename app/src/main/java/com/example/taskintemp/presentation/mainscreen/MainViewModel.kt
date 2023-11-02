package com.example.taskintemp.presentation.mainscreen

import androidx.lifecycle.viewModelScope
import com.example.taskintemp.data.remote.dto.DateTimeDto
import com.example.taskintemp.data.remote.dto.toDateModel
import com.example.taskintemp.domain.model.DateModel
import com.example.taskintemp.domain.model.ScreenState
import com.example.taskintemp.domain.usesCases.CheckInUseCases
import com.example.taskintemp.presentation.common.BaseViewModel
import com.example.taskintemp.util.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(private val useCases: CheckInUseCases) : BaseViewModel<DateModel>() {

    init {
        getDateTimeFromApi()
    }

    private fun getDateTimeFromApi() {
//        useCases.getApiDateTime().onEach { enable when api is needed
        useCases.getMockedApiDateTime().onEach {
            when (it) {
                is NetworkResource.Success -> {

                    val date = it.data?.toDateModel()
                    //_state.value = ScreenState(receivedResponse = it.data)
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

}