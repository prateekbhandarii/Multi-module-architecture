package com.pb.news.domain

import com.pb.common.di.Result
import com.pb.common.models.Article
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {

    suspend fun getNews(): Flow<Result<MutableList<Article>>>

}