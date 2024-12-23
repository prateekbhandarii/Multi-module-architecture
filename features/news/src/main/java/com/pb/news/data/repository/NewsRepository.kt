package com.pb.news.data.repository

import com.pb.common.di.Result
import com.pb.common.models.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNews(): Flow<Result<MutableList<Article>>>

}