package com.helios.composeinstagram.presentation.signup

import com.helios.composeinstagram.common.state.EmailState
import com.helios.composeinstagram.common.state.PasswordState
import com.helios.composeinstagram.common.state.UsernameState
import com.helios.composeinstagram.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState: MutableStateFlow<SignUpViewState> = MutableStateFlow(SignUpViewState())
    val uiState: StateFlow<SignUpViewState> = _uiState.asStateFlow()
}

data class SignUpViewState(
    var usernameState: UsernameState = UsernameState(),
    var passwordState: PasswordState = PasswordState(),
    var emailState: EmailState = EmailState(),
)