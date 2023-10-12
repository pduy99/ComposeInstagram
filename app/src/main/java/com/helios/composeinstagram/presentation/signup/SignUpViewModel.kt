package com.helios.composeinstagram.presentation.signup

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.helios.composeinstagram.common.state.EmailState
import com.helios.composeinstagram.common.state.PasswordState
import com.helios.composeinstagram.common.state.UsernameState
import com.helios.composeinstagram.common.view.BaseViewModel
import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<SignUpViewState> = MutableStateFlow(SignUpViewState())
    val uiState: StateFlow<SignUpViewState> = _uiState.asStateFlow()

    fun signup() {
        val username = _uiState.value.usernameState.text
        val password = _uiState.value.passwordState.text
        val email = _uiState.value.emailState.text

        userRepository.createUser(email, password, username).onEach {
            when (it) {
                is DataResult.Loading -> {
                    showLoading()
                }

                is DataResult.Success -> {
                    Log.d(TAG, "Sign up success")
                    hideLoading()
                }

                is DataResult.Error -> {
                    hideLoading()
                    Log.d(TAG, "Error: ${it.throwable.localizedMessage}")
                    it.throwable.printStackTrace()
                    setError(it.throwable.localizedMessage ?: "Something went wrong")
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class SignUpViewState(
    var usernameState: UsernameState = UsernameState(),
    var passwordState: PasswordState = PasswordState(),
    var emailState: EmailState = EmailState(),
) {
    val isButtonEnabled: Boolean
        get() {
            return usernameState.isValid && passwordState.isValid && emailState.isValid
        }
}