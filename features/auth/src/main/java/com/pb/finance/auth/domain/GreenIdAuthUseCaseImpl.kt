package com.pb.finance.auth.domain

import android.net.Uri
import com.pb.common.models.GreenIdAuthState
import com.pb.common.usecase.GreenIdAuthUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GreenIdAuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : GreenIdAuthUseCase {
    override fun handleGreenIdDeepLink(uri: Uri): GreenIdAuthState {
        val token = uri.getQueryParameter("token")
        val isCanceled = uri.getQueryParameter("cancel")?.toBoolean() ?: false

        return when {
            token != null && !isCanceled -> {
                authRepository.saveAuthToken(token)
                // Green ID success case: token is present, and cancellation is not true
                GreenIdAuthState.Authenticated("Authentication successful with token: $token")
            }

            isCanceled && token != null -> {
                // Green ID failure case: cancellation is true, with token
                GreenIdAuthState.Canceled("Authentication failed: User canceled the action. Token: $token")
            }

            isCanceled -> {
                // General failure case: cancellation is true without a token
                GreenIdAuthState.Failure("Authentication failed: User canceled the action.")
            }

            else -> {
                // Invalid or missing token case
                GreenIdAuthState.Failure("Invalid or missing token.")
            }
        }

    }
}


