package com.pb.user.domain

import com.pb.common.models.UserInfo
import com.pb.common.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {

    override fun getUserInfo(): UserInfo {
        return UserInfo(
            id = "123", name = "John Doe", email = "john.doe@example.com",
            address = ""
        )
    }
}