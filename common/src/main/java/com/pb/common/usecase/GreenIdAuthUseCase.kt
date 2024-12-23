package com.pb.common.usecase

import android.net.Uri
import com.pb.common.models.GreenIdAuthState

interface GreenIdAuthUseCase {
    fun handleGreenIdDeepLink(uri: Uri): GreenIdAuthState
}
