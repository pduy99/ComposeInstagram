package com.helios.composeinstagram.data.repository.impl

import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.data.datasource.UserDataSource
import com.helios.composeinstagram.data.model.User
import com.helios.composeinstagram.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteUserRepository @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {

    override fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): Flow<DataResult<User>> {
        return userDataSource.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun authenticate(email: String, password: String) {
        return userDataSource.authenticate(email, password)
    }

    override fun getCurrentUser(): Flow<DataResult<User?>> {
        return userDataSource.getCurrentUser()
    }

    override fun getUserByUserName(userName: String): Flow<DataResult<User?>> {
        return userDataSource.getUserByUserName(userName)
    }

    override fun getUserByUserId(id: String): Flow<DataResult<User?>> {
        return userDataSource.getUserByUserId(id)
    }

    override suspend fun updateUserData(user: User) {
        return userDataSource.updateUserData(user)
    }

    override suspend fun removeCurrentUser() {
        return userDataSource.removeCurrentUser()
    }
}
