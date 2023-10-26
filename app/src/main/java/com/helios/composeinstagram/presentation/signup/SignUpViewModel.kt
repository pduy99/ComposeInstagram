package com.helios.composeinstagram.presentation.signup

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.helios.composeinstagram.common.state.EmailState
import com.helios.composeinstagram.common.state.PasswordState
import com.helios.composeinstagram.common.state.UsernameState
import com.helios.composeinstagram.common.view.BaseViewModel
import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.common.view.doIfError
import com.helios.composeinstagram.common.view.doIfSuccess
import com.helios.composeinstagram.data.model.User
import com.helios.composeinstagram.data.repository.UserRepository
import com.helios.composeinstagram.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<SignUpViewState> = MutableStateFlow(SignUpViewState())
    val uiState: StateFlow<SignUpViewState> = _uiState.asStateFlow()

    fun signup() {
        val username = _uiState.value.usernameState.text
        val password = _uiState.value.passwordState.text
        val email = _uiState.value.emailState.text

        viewModelScope.launch(Dispatchers.IO) {
            showLoading()
            val result = signUpUseCase(email, password, username)

            result.doIfSuccess {

            }

            result.doIfError {

            }
        }
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
