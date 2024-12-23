package com.pb.common.models

sealed class GreenIdAuthState {
    data object Idle : GreenIdAuthState()
    data class RedirectToBrowser(val url: String) : GreenIdAuthState()
    data class Authenticated(val message: String) : GreenIdAuthState()
    data class Canceled(val message: String) : GreenIdAuthState()
    data class Failure(val error: String) : GreenIdAuthState()
}
