package com.pb.profile.domain

import com.pb.common.models.UserInfo

interface ProfileUseCase {

    suspend fun getUser(): UserInfo

}