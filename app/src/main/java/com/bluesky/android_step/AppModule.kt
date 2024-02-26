package com.bluesky.android_step

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideUserDataSource(): UserDataSource = UserDataSource()

    @Provides
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository = UserRepository(userDataSource)
}