package com.pb.headlines.data.repository

import com.pb.common.di.Result
import com.pb.common.models.Article
import kotlinx.coroutines.flow.Flow

interface HeadlinesRepository {

    suspend fun getHeadlines(): Flow<Result<MutableList<Article>>>

}