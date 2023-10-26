package com.helios.composeinstagram.domain.usecases

import com.helios.composeinstagram.common.view.getDataIfSuccessOrNull
import com.helios.composeinstagram.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheckUsernameExistedUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(username: String): Boolean {
        return userRepository.getUserByUserName(username).first().getDataIfSuccessOrNull() != null
    }
}
