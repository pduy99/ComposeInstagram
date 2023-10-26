package com.helios.composeinstagram.data.repository

import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun createUserWithEmailAndPassword(email: String, password: String): Flow<DataResult<User>>

    suspend fun authenticate(email: String, password: String)

    fun getCurrentUser(): Flow<DataResult<User?>>

    fun getUserByUserName(userName: String): Flow<DataResult<User?>>

    fun getUserByUserId(id: String): Flow<DataResult<User?>>

    suspend fun updateUserData(user: User)

    suspend fun removeCurrentUser()
}
