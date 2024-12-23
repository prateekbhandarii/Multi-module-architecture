package com.pb.finance.auth.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pb.common.models.GreenIdAuthState
import com.pb.common.usecase.AuthStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthNavigationViewModel @Inject constructor() : ViewModel() {


    private val _authState = MutableStateFlow<GreenIdAuthState>(GreenIdAuthState.Idle)
    val authState: StateFlow<GreenIdAuthState> get() = _authState

    private val _uiState = mutableStateOf<GreenIdAuthState>(GreenIdAuthState.Idle)
    val uiState: State<GreenIdAuthState> get() = _uiState

    fun initiateAuth() {
        val authUrl = "https://plum-elaina-87.tiiny.site/index.html"
        _uiState.value = GreenIdAuthState.RedirectToBrowser(authUrl)
    }

    init {
        // Use a shared state manager or a singleton to get the initial state if needed
        _authState.value = AuthStateManager.getState()
    }

    fun updateState(newState: GreenIdAuthState) {
        _authState.value = newState
        _uiState.value = _authState.value

    }
}
