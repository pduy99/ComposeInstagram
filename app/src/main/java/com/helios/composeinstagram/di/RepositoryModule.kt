package com.helios.composeinstagram.di

import com.helios.composeinstagram.data.datasource.UserDataSource
import com.helios.composeinstagram.data.repository.UserRepository
import com.helios.composeinstagram.data.repository.remote.FirebaseUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(dataSource: UserDataSource): UserRepository {
        return FirebaseUserRepository(dataSource)
    }
}