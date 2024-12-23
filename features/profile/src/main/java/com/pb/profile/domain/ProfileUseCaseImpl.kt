package com.pb.profile.domain

import com.pb.common.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : ProfileUseCase {
    override suspend fun getUser() = userRepository.getUserInfo()
}