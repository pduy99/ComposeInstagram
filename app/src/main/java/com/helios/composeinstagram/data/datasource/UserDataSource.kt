package com.helios.composeinstagram.data.datasource

import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.data.model.User
import kotlinx.coroutines.flow.Flow

const val USERS = "users"

interface UserDataSource {

    fun authenticate(email: String, password: String): Flow<DataResult<Boolean>>

    fun getCurrentUser(): Flow<DataResult<User?>>

    fun getUserByUserName(userName: String): Flow<DataResult<User?>>

    fun getUserByUserId(id: String): Flow<DataResult<User?>>

    fun updateUserData(user: User): Flow<DataResult<Unit>>

    fun createUserWithEmailAndPassword(email: String, password: String): Flow<DataResult<User>>

    fun removeUser(userId: String): Flow<DataResult<Unit>>
}