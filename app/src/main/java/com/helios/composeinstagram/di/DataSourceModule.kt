package com.helios.composeinstagram.di

import com.helios.composeinstagram.data.datasource.UserDataSource
import com.helios.composeinstagram.data.datasource.impl.RemoteUserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindUserDataSource(impl: RemoteUserDataSource): UserDataSource
}
