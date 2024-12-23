package com.pb.headlines.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pb.common.di.Result
import com.pb.common.models.Article
import com.pb.headlines.domain.HeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    private val headLinesUseCase: HeadlinesUseCase
) : ViewModel() {

    private val _headLinesNews = MutableStateFlow<Result<MutableList<Article>>>(Result.loading())
    val headlinesNews: StateFlow<Result<List<Article>>> = _headLinesNews.asStateFlow()

    init {
        fetchHeadlines()
    }


    private fun fetchHeadlines() {
        viewModelScope.launch {
            try {
                headLinesUseCase.getHeadlines()
                    .onStart { _headLinesNews.value = Result.loading() }
                    .collect { result ->
                        _headLinesNews.value = result
                    }
            } catch (e: Exception) {
                _headLinesNews.value = Result.error("Failed to fetch news: ${e.message}")
            }
        }
    }
}
