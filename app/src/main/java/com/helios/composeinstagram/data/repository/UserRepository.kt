package com.helios.composeinstagram.data.repository

import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun createUser(email: String, password: String, userName: String): Flow<DataResult<User>>

    fun authenticate(email: String, password: String): Flow<DataResult<Boolean>>

    fun getCurrentUser(): Flow<DataResult<User?>>

    fun getUserByUserName(userName: String): Flow<DataResult<User?>>

    fun getUserByUserId(id: String): Flow<DataResult<User?>>

    fun updateUserData(user: User): Flow<DataResult<Unit>>
}