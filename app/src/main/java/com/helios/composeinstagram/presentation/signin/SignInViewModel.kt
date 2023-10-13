package com.helios.composeinstagram.presentation.signin

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.helios.composeinstagram.common.state.EmailState
import com.helios.composeinstagram.common.state.PasswordState
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
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<SignInViewState> = MutableStateFlow(SignInViewState())
    val uiState: StateFlow<SignInViewState> = _uiState.asStateFlow()

    fun signIn() {
        val email = _uiState.value.emailState.text
        val password = _uiState.value.passwordState.text

        userRepository.authenticate(email = email, password = password).onEach {
            when (it) {
                is DataResult.Loading -> {
                    showLoading()
                }

                is DataResult.Success -> {
                    hideLoading()
                    Log.d(TAG, "Success")
                    _uiState.value = _uiState.value.copy(isSignInSuccess = true)
                }

                is DataResult.Error -> {
                    hideLoading()
                    setError(it.throwable.localizedMessage ?: "Something went wrong")
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class SignInViewState(
    val emailState: EmailState = EmailState(),
    val passwordState: PasswordState = PasswordState(),
    val isSignInSuccess: Boolean = false
) {
    val isButtonEnabled: Boolean
        get() {
            return emailState.text.isNotBlank() && passwordState.text.isNotBlank()
        }
}
