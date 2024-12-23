package com.pb.headlines.domain

import com.pb.common.di.Result
import com.pb.common.models.Article
import kotlinx.coroutines.flow.Flow

interface HeadlinesUseCase {

    suspend fun getHeadlines(): Flow<Result<MutableList<Article>>>

}