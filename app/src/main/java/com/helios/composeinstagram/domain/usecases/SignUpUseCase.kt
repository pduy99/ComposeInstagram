package com.helios.composeinstagram.domain.usecases

import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.common.view.doIfError
import com.helios.composeinstagram.common.view.doIfSuccess
import com.helios.composeinstagram.data.model.User
import com.helios.composeinstagram.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SignUpUseCase @Inject constructor(
    private val checkUsernameExistedUseCase: CheckUsernameExistedUseCase,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(email: String, password: String, username: String): DataResult<User> {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                if (checkUsernameExistedUseCase(username)) {
                    continuation.resume(DataResult.Error(Throwable("Username already existed")))
                } else {
                    userRepository.createUserWithEmailAndPassword(email, password).collectLatest {
                        it.doIfSuccess { user ->
                            try {
                                userRepository.updateUserData(user.copy(username = username))
                            } catch (ex: Exception) {
                                userRepository.removeCurrentUser()
                                continuation.resume(DataResult.Error(ex))
                            }
                        }
                        it.doIfError { error ->
                            continuation.resume(DataResult.Error(error))
                        }
                    }
                }
            }
        }
    }
}
