package com.pb.news.domain

import com.pb.news.data.repository.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsUseCaseImpl @Inject constructor(private val repository: NewsRepository) :
    NewsUseCase {
    override suspend fun getNews() = repository.getNews()
}