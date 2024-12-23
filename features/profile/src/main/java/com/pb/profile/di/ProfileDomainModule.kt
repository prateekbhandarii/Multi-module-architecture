package com.pb.profile.di

import com.pb.profile.domain.ProfileUseCase
import com.pb.profile.domain.ProfileUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileDomainModule {

    @Binds
    internal abstract fun bindsUseCase(
        useCaseImpl: ProfileUseCaseImpl
    ): ProfileUseCase
}