package com.example.taskintemp.presentation.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.taskintemp.domain.model.ScreenState

open class BaseViewModel<T> : ViewModel() {

    protected val _state = mutableStateOf(ScreenState<T>())
    val state: State<ScreenState<T>> = _state


}