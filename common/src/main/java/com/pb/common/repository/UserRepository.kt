package com.pb.common.repository

import com.pb.common.models.UserInfo

interface UserRepository {
    fun getUserInfo(): UserInfo
}
