package com.helios.composeinstagram.common.view

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel() {

    protected val TAG = this::class.java.simpleName

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorEvent: MutableStateFlow<ErrorEvent> = MutableStateFlow(ErrorEvent(""))
    val errorEvent: StateFlow<ErrorEvent> = _errorEvent

    protected fun showLoading() {
        _isLoading.value = true
    }

    protected fun hideLoading() {
        _isLoading.value = false
    }

    protected fun setError(errorMessage: String) {
        _errorEvent.value = ErrorEvent(errorMessage)
    }
}