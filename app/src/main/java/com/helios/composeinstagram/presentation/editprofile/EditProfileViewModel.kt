package com.helios.composeinstagram.presentation.editprofile

import androidx.lifecycle.viewModelScope
import com.helios.composeinstagram.common.view.BaseViewModel
import com.helios.composeinstagram.common.view.doIfError
import com.helios.composeinstagram.common.view.doIfSuccess
import com.helios.composeinstagram.data.model.Gender
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
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<EditProfileUiState> =
        MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    init {
        showLoading()
        userRepository.getCurrentUser().onEach {
            it.doIfSuccess { user ->
                hideLoading()
                _uiState.value = _uiState.value.copy(user)
            }
            it.doIfError { throwable ->
                hideLoading()
                setError(throwable.localizedMessage ?: "Something went wrong")
            }
        }.launchIn(viewModelScope)
    }

    fun onUsernameChanged(username: String) {
        _uiState.value =
            _uiState.value.copy(userData = _uiState.value.userData?.copy(username = username))
    }

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(userData = _uiState.value.userData?.copy(name = name))
    }

    fun onBioChanged(bio: String) {
        _uiState.value = _uiState.value.copy(userData = _uiState.value.userData?.copy(bio = bio))
    }

    fun onGenderChanged(gender: String) {
        _uiState.value =
            _uiState.value.copy(userData = _uiState.value.userData?.copy(gender = gender))
    }

    fun updateUserData() {
        _uiState.value.userData?.let { user ->
            showLoading()
            userRepository.updateUserData(user).onEach {
                it.doIfSuccess {
                    hideLoading()
                    _uiState.value = _uiState.value.copy(isSuccess = true)
                }
                it.doIfError { throwable ->
                    hideLoading()
                    setError(
                        throwable.localizedMessage
                            ?: "Failed to update information, try again later"
                    )
                }
            }.launchIn(viewModelScope)
        }
    }
}

data class EditProfileUiState(
    val userData: User? = null,
    val isSuccess: Boolean = false,
    val dropDownItems: List<String> = Gender.values().map { it.name },
)
