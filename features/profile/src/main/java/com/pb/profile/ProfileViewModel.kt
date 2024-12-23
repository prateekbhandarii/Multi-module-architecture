package com.pb.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pb.common.models.UserInfo
import com.pb.profile.domain.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase
) : ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    init {
        viewModelScope.launch {
            _userInfo.value = useCase.getUser()
        }
    }
}
