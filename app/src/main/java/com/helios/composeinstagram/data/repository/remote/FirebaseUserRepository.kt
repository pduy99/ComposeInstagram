package com.helios.composeinstagram.data.repository.remote

import android.util.Log
import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.common.view.doIfError
import com.helios.composeinstagram.common.view.doIfSuccess
import com.helios.composeinstagram.data.datasource.UserDataSource
import com.helios.composeinstagram.data.model.User
import com.helios.composeinstagram.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "FirebaseUserRepository"

class FirebaseUserRepository @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {

    override fun createUser(
        email: String,
        password: String,
        userName: String
    ): Flow<DataResult<User>> = flow {
        emit(DataResult.Loading)
        userDataSource.createUserWithEmailAndPassword(email, password).collect { createUserResult ->
            Log.d(TAG, "createUserResult: $createUserResult")
            createUserResult.doIfSuccess { authenticatedUser ->
                userDataSource.getUserByUserName(userName).collect { checkUserResult ->
                    checkUserResult.doIfSuccess { checkUser ->
                        if (checkUser != null) {
                            emit(DataResult.Error(Throwable("The username is already existed")))
                            userDataSource.removeUser(authenticatedUser.userId!!).collect()
                        } else {
                            val newUser = authenticatedUser.copy(username = userName, email = email)
                            Log.d(TAG, "updateUserData")
                            userDataSource.updateUserData(newUser).collect { updateUserResult ->
                                updateUserResult.doIfSuccess {
                                    emit(DataResult.Success(newUser))
                                }
                                updateUserResult.doIfError {
                                    userDataSource.removeUser(authenticatedUser.userId!!).collect()
                                    emit(DataResult.Error(it))
                                }
                            }
                        }
                    }
                    checkUserResult.doIfError {
                        userDataSource.removeUser(authenticatedUser.userId!!).collect()
                        emit(DataResult.Error(it))
                    }
                }
            }
            createUserResult.doIfError {
                emit(DataResult.Error(it))
            }
        }
    }

    override fun authenticate(email: String, password: String): Flow<DataResult<Boolean>> {
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

    override fun updateUserData(user: User): Flow<DataResult<Unit>> {
        return userDataSource.updateUserData(user)
    }
}