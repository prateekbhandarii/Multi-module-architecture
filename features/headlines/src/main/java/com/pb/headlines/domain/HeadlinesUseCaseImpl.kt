package com.pb.headlines.domain

import com.pb.headlines.data.repository.HeadlinesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeadlinesUseCaseImpl @Inject constructor(private val repository: HeadlinesRepository) :
    HeadlinesUseCase {
    override suspend fun getHeadlines() = repository.getHeadlines()
}