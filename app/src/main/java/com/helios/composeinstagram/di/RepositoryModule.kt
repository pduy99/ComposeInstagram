package com.helios.composeinstagram.di

import com.helios.composeinstagram.data.repository.UserRepository
import com.helios.composeinstagram.data.repository.impl.RemoteUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(impl: RemoteUserRepository): UserRepository
}
