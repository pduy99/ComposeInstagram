package com.helios.composeinstagram.presentation.splash

import androidx.lifecycle.viewModelScope
import com.helios.composeinstagram.common.view.BaseViewModel
import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.common.view.doIfError
import com.helios.composeinstagram.common.view.doIfSuccess
import com.helios.composeinstagram.data.model.User
import com.helios.composeinstagram.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    userRepository: UserRepository
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<SplashUiState> = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fun handleGetCurrentResult(result: DataResult<User?>) {
                result.doIfSuccess { user ->
                    if (user != null) {
                        _uiState.value = _uiState.value.copy(isSignedIn = true)
                    } else {
                        _uiState.value = _uiState.value.copy(isNeedSignIn = true)
                    }
                }
                result.doIfError { throwable ->
                    setError(throwable.localizedMessage ?: "Something went wrong")
                }
            }

            userRepository.getCurrentUser()
                .flowOn(Dispatchers.IO)
                .onEach { handleGetCurrentResult(it) }
                .launchIn(viewModelScope)
        }
    }
}

data class SplashUiState(
    var isSignedIn: Boolean = false,
    var isNeedSignIn: Boolean = false,
)
