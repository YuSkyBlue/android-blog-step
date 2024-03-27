package com.bluesky.android_step

class UserRepository(private val userDataSource: UserDataSource) {
    fun getUserData(): String {
        return userDataSource.getUserData()
    }
}