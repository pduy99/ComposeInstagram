package com.helios.composeinstagram.presentation.signin

import androidx.lifecycle.viewModelScope
import com.helios.composeinstagram.common.state.EmailState
import com.helios.composeinstagram.common.state.PasswordState
import com.helios.composeinstagram.common.view.BaseViewModel
import com.helios.composeinstagram.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<SignInViewState> = MutableStateFlow(SignInViewState())
    val uiState: StateFlow<SignInViewState> = _uiState.asStateFlow()

    fun signIn() {
        val email = _uiState.value.emailState.text
        val password = _uiState.value.passwordState.text

        viewModelScope.launch(Dispatchers.IO) {
            try {
                showLoading()
                signInUseCase(email, password)
                hideLoading()
                _uiState.value = _uiState.value.copy(isSignInSuccess = true)
            } catch (ex: Exception) {
                hideLoading()
                setError(ex.localizedMessage ?: "Something went wrong")
            }
        }
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
