package com.helios.composeinstagram.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.helios.composeinstagram.data.datasource.UserDataSource
import com.helios.composeinstagram.data.datasource.remote.FirebaseUserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideUserDataSource(auth: FirebaseAuth, db: FirebaseFirestore): UserDataSource {
        return FirebaseUserDataSource(auth, db)
    }
}