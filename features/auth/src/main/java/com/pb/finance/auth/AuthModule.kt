package com.pb.finance.auth

import com.pb.finance.auth.domain.GreenIdAuthUseCaseImpl
import com.pb.common.usecase.GreenIdAuthUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {
    @Binds
    fun bindHandleDeepLinkUseCase(
        handleDeepLinkUseCase: GreenIdAuthUseCaseImpl
    ): GreenIdAuthUseCase
}
