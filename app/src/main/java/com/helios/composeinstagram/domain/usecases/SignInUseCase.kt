package com.helios.composeinstagram.domain.usecases

import com.helios.composeinstagram.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val defaultCoroutineScope: CoroutineScope
) {

    suspend operator fun invoke(email: String, password: String) {
        defaultCoroutineScope.launch {
            userRepository.authenticate(email, password)
        }
    }
}
