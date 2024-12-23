package com.pb.common.usecase

import com.pb.common.models.GreenIdAuthState

object AuthStateManager {
    private var _authState: GreenIdAuthState = GreenIdAuthState.Idle

    fun setState(state: GreenIdAuthState) {
        _authState = state
    }

    fun getState(): GreenIdAuthState = _authState
}
