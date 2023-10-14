package com.helios.composeinstagram.presentation.profile

import androidx.lifecycle.viewModelScope
import com.helios.composeinstagram.common.view.BaseViewModel
import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.data.model.User
import com.helios.composeinstagram.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    userRepository: UserRepository
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        userRepository.getCurrentUser().onEach {
            when (it) {
                is DataResult.Loading -> {
                    showLoading()
                }

                is DataResult.Success -> {
                    hideLoading()
                    _uiState.value = _uiState.value.copy(it.data)
                }

                is DataResult.Error -> {
                    hideLoading()
                    setError(it.throwable.localizedMessage ?: "Something went wrong")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateActiveTab(newActiveTab: ProfileTab) {
        if (_uiState.value.activeTab != newActiveTab) {
            _uiState.value = _uiState.value.copy(activeTab = newActiveTab)
        }
    }
}

data class ProfileUiState(
    val userData: User? = null,
    val activeTab: ProfileTab = ProfileTab.Posts
)
