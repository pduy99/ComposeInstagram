package com.helios.composeinstagram.data.datasource.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.snapshots
import com.helios.composeinstagram.common.view.DataResult
import com.helios.composeinstagram.data.datasource.USERS
import com.helios.composeinstagram.data.datasource.UserDataSource
import com.helios.composeinstagram.data.model.User
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : UserDataSource {

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override fun getCurrentUser(): Flow<DataResult<User?>> {
        return getUserByUserId(auth.currentUser?.uid ?: "")
    }

    override fun getUserByUserName(userName: String): Flow<DataResult<User?>> {
        return db.collection(USERS)
            .whereEqualTo(User.USERNAME, userName)
            .snapshots()
            .map { querySnapshot ->
                return@map querySnapshot.documents.firstOrNull()?.let { document ->
                    DataResult.Success(document.toObject<User>())
                } ?: DataResult.Success(null)
            }
    }

    override fun getUserByUserId(id: String): Flow<DataResult<User?>> {
        Log.d("DUY", "getUserByUserId")
        return db.collection(USERS)
            .whereEqualTo(User.USER_ID, id)
            .snapshots()
            .map { querySnapshot ->
                return@map querySnapshot.documents.firstOrNull()?.let { document ->
                    DataResult.Success(document.toObject<User>())
                } ?: DataResult.Success(null)
            }
    }

    override suspend fun updateUserData(user: User) {
        user.userId?.let { userId ->
            db.collection(USERS).document(userId).set(user).await()
        }
    }

    override fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<DataResult<User>> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            trySend(DataResult.Success(User(userId = it.user?.uid, email = email)))
        }.addOnFailureListener {
            trySend(DataResult.Error(it))
        }

        awaitClose { cancel() }
    }

    override suspend fun removeCurrentUser() {
        auth.currentUser?.delete()?.await()
    }
}
