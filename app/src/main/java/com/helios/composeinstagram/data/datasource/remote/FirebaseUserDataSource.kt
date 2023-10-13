package com.helios.composeinstagram.data.datasource.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.common.view.doIfError
import com.helios.composeinstagram.common.view.doIfSuccess
import com.helios.composeinstagram.data.datasource.USERS
import com.helios.composeinstagram.data.datasource.UserDataSource
import com.helios.composeinstagram.data.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val TAG = "FirebaseUserDataSource"

class FirebaseUserDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : UserDataSource {

    override fun authenticate(email: String, password: String): Flow<DataResult<Boolean>> =
        callbackFlow {
            trySend(DataResult.Loading)
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                trySend(DataResult.Success(it.user != null))
            }.addOnFailureListener {
                trySend(DataResult.Error(it))
            }

            awaitClose()
        }

    override fun getCurrentUser(): Flow<DataResult<User?>> = callbackFlow {
        trySend(DataResult.Loading)
        try {
            auth.currentUser?.uid?.let { userId ->
                getUserByUserId(userId).collect { dataResult ->
                    dataResult.doIfSuccess { user ->
                        trySend(DataResult.Success(user))
                    }
                    dataResult.doIfError { throwable ->
                        trySend(DataResult.Error(throwable))
                    }
                }
            } ?: trySend(DataResult.Success(null))
        } catch (exception: Exception) {
            trySend(DataResult.Error(exception))
        }

        awaitClose()
    }

    override fun getUserByUserName(userName: String): Flow<DataResult<User?>> = callbackFlow {
        Log.d(TAG, "getUserByUserName")
        trySend(DataResult.Loading)
        db.collection(USERS).whereEqualTo(User.USERNAME, userName).get()
            .addOnSuccessListener { snapshot ->
                snapshot.documents.firstOrNull()?.let { firstSnapshot ->
                    val user = firstSnapshot.toObject<User>()
                    trySend(DataResult.Success(user))
                } ?: trySend(DataResult.Success(null))
            }.addOnFailureListener {
                trySend(DataResult.Error(it))
            }

        awaitClose()
    }

    override fun getUserByUserId(id: String): Flow<DataResult<User?>> = callbackFlow {
        trySend(DataResult.Loading)
        db.collection(USERS).whereEqualTo(User.USER_ID, id).get().addOnSuccessListener { snapshot ->
            snapshot.documents.firstOrNull()?.let { firstSnapshot ->
                val user = firstSnapshot.toObject<User>()
                trySend(DataResult.Success(user))
            } ?: trySend(DataResult.Success(null))
        }.addOnFailureListener {
            trySend(DataResult.Error(it))
        }

        awaitClose()
    }

    override fun updateUserData(user: User): Flow<DataResult<Unit>> = callbackFlow {
        trySend(DataResult.Loading)
        user.userId?.let { userId ->
            db.collection(USERS).document(userId).set(user).addOnSuccessListener {
                trySend(DataResult.Success(Unit))
            }.addOnFailureListener { exception ->
                trySend(DataResult.Error(exception))
            }
        } ?: run {
            trySend(DataResult.Error(NullPointerException("User id must not be null")))
        }

        awaitClose()
    }

    override fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<DataResult<User>> = callbackFlow {
        trySend(DataResult.Loading)
        Log.d(TAG, "createUserWithEmailAndPassword")
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            Log.d(TAG, "DataResult.Success")
            trySend(DataResult.Success(User(userId = it.user!!.uid)))
        }.addOnFailureListener {
            Log.d(TAG, "DataResult.Error(it)")
            trySend(DataResult.Error(it))
        }

        awaitClose()
    }

    override fun removeUser(userId: String): Flow<DataResult<Unit>> = callbackFlow {
        auth.currentUser?.delete()?.addOnSuccessListener {
            trySend(DataResult.Success(Unit))
        }?.addOnFailureListener {
            trySend(DataResult.Error(it))
        }

        awaitClose()
    }
}
