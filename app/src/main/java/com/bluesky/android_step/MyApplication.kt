package com.bluesky.android_step

import android.app.Application

class UserRepository(private val userDataSource: UserDataSource)

class UserDataSource

class MyApplication : Application() {
    val userDataSource by lazy { UserDataSource() }
    val userRepository by lazy { UserRepository(userDataSource) }
}