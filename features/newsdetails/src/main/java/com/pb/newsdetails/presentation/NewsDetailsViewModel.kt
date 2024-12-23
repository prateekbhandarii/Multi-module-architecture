package com.pb.newsdetails.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.pb.newsdetails.nav.newsUrlArg
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _articleUrl = mutableStateOf<String?>(null)
    val articleUrl: State<String?> = _articleUrl

    init {
        _articleUrl.value = savedStateHandle.get<String>(newsUrlArg)
    }
}
